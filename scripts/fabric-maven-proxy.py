#!/usr/bin/env python3
"""
Fabric Maven 反向代理（开发环境辅助工具）

作用：串行 + 重试转发上游 Maven 仓库请求，规避 Java/Python 在 Cloudflare
      等 CDN 后端的并发 TLS 握手间歇性失败。上游下载使用 curl（openssl），
      其 TLS 实现对本机 CDN 并发场景稳定。

设计：多线程接受连接（不阻塞 Gradle 并发请求），全局锁串行化上游访问，
      每个请求带重试，命中即返回内存缓存。

用法：
    # Fabric maven（默认）
    python3 scripts/fabric-maven-proxy.py
    # Mojang libraries
    PROXY_UPSTREAM=https://libraries.minecraft.net PROXY_PORT=8889 \\
        python3 scripts/fabric-maven-proxy.py

停止：Ctrl+C
"""
import http.server
import subprocess
import tempfile
import os
import time
import threading

UPSTREAM = os.environ.get("PROXY_UPSTREAM", "https://maven.fabricmc.net")
LISTEN = ("127.0.0.1", int(os.environ.get("PROXY_PORT", "8888")))
MAX_RETRY = 6
_upstream_lock = threading.Lock()
_cache = {}  # path -> (status, data)


class Handler(http.server.BaseHTTPRequestHandler):
    protocol_version = "HTTP/1.1"

    def _forward(self, method):
        cached = _cache.get(self.path)
        if cached is not None:
            self._send(cached[0], cached[1])
            return
        url = UPSTREAM + self.path
        last = None
        for attempt in range(MAX_RETRY):
            try:
                code, data = self._curl(method, url)
                if code > 0:
                    if code == 200:
                        _cache[self.path] = (code, data)
                    self._send(code, data)
                    return
                last = "curl code={}".format(code)
            except Exception as e:
                last = str(e)
            time.sleep(min(0.5 * (attempt + 1), 2.0))
        self._send(502, b"")

    def _curl(self, method, url):
        with tempfile.NamedTemporaryFile(delete=False) as tf:
            tmp = tf.name
        try:
            proc = subprocess.run(
                ["curl", "-sS", "-L", "--max-time", "40",
                 "-X", method, "-A", "fabric-maven-proxy/1.0",
                 "-o", tmp, "-w", "%{http_code}", url],
                capture_output=True, timeout=45,
            )
            code_str = proc.stdout.decode().strip()
            code = int(code_str) if code_str.isdigit() else 0
            with open(tmp, "rb") as f:
                data = f.read()
            return code, data
        finally:
            try:
                os.unlink(tmp)
            except OSError:
                pass

    def _send(self, status, data):
        self.send_response(status)
        self.send_header("Content-Type", "application/octet-stream")
        self.send_header("Content-Length", str(len(data)))
        self.end_headers()
        if data and self.command != "HEAD":
            self.wfile.write(data)

    def do_GET(self):
        self._forward("GET")

    def do_HEAD(self):
        self._forward("HEAD")

    def log_message(self, *args):
        pass


class Server(http.server.ThreadingHTTPServer):
    daemon_threads = True

    def handle_error(self, request, client_address):
        # 静默 gradle 连接池管理导致的 ConnectionResetError
        pass


if __name__ == "__main__":
    server = Server(LISTEN, Handler)
    print(f"[fabric-maven-proxy] listening on http://{LISTEN[0]}:{LISTEN[1]}"
          f" -> {UPSTREAM} (curl upstream, serial, retry={MAX_RETRY})",
          flush=True)
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("\n[fabric-maven-proxy] stopped")
