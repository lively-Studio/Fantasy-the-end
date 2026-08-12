#!/usr/bin/env python3
"""
用新的资源文件更新模组 JAR，并安装到 mods 目录
"""
import os
import shutil
import zipfile
import tempfile

JAR_PATH = "/Users/cangcang/code/Fantasy- The End/build/libs/fantasy-the-end-1.0.0.jar"
SRC_RES = "/Users/cangcang/code/Fantasy- The End/src/main/resources"
MODS_DIR = os.path.expanduser("~/Library/Application Support/minecraft/versions/1.21.11-Fabric-CODER-mod/mods")
BACKUP_DIR = os.path.expanduser("~/Library/Application Support/minecraft/versions/1.21.11-Fabric-CODER-mod/mods/.backup")

def update_jar():
    # 临时目录
    tmpdir = tempfile.mkdtemp(prefix="fantasy_jar_")
    try:
        print(f"[1/5] 解压 JAR 到: {tmpdir}")
        with zipfile.ZipFile(JAR_PATH, "r") as zf:
            zf.extractall(tmpdir)

        print(f"[2/5] 复制新资源从: {SRC_RES}")
        # 复制 assets 和 data 目录
        for sub in ["assets", "data"]:
            src_sub = os.path.join(SRC_RES, sub)
            dst_sub = os.path.join(tmpdir, sub)
            if os.path.exists(src_sub):
                # 删除旧的，复制新的
                if os.path.exists(dst_sub):
                    shutil.rmtree(dst_sub)
                shutil.copytree(src_sub, dst_sub)
                print(f"       - 更新了 {sub}/")

        # 也复制根目录文件 fabric.mod.json, icon.png 等
        for f in os.listdir(SRC_RES):
            src_f = os.path.join(SRC_RES, f)
            if os.path.isfile(src_f):
                shutil.copy2(src_f, os.path.join(tmpdir, f))
                print(f"       - 更新了 {f}")

        print("[3/5] 重新打包 JAR")
        new_jar = JAR_PATH + ".new"
        with zipfile.ZipFile(new_jar, "w", zipfile.ZIP_DEFLATED) as zf:
            for root, dirs, files in os.walk(tmpdir):
                for name in files:
                    full = os.path.join(root, name)
                    arc = os.path.relpath(full, tmpdir)
                    zf.write(full, arc)
        os.replace(new_jar, JAR_PATH)
        print(f"       JAR 已保存: {JAR_PATH}")

        print("[4/5] 备份旧 mod 并安装")
        os.makedirs(BACKUP_DIR, exist_ok=True)
        dst_jar = os.path.join(MODS_DIR, "fantasy-the-end-1.0.0.jar")
        if os.path.exists(dst_jar):
            import datetime
            ts = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
            backup = os.path.join(BACKUP_DIR, f"fantasy-the-end-1.0.0-{ts}.jar")
            shutil.copy2(dst_jar, backup)
            print(f"       旧版本已备份: {backup}")
        shutil.copy2(JAR_PATH, dst_jar)
        print(f"       已安装到: {dst_jar}")

        print("[5/5] 完成!")
        print("\n重要提示：请完全关闭 Minecraft 并重新启动，才能加载新的资源内容。")
        print("（Fabric 会在 mods/.fabric/ 目录缓存旧模组内容）")
    finally:
        shutil.rmtree(tmpdir, ignore_errors=True)

if __name__ == "__main__":
    update_jar()
