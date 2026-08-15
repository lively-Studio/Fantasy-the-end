#!/usr/bin/env python3
"""
生成可驯服末影人实体纹理和GUI纹理。
实体纹理基于原版末影人风格，采用紫色主题。
"""

import os
from PIL import Image, ImageDraw

# 纹理输出路径
ASSETS_DIR = os.path.join(os.path.dirname(__file__),
    "src", "main", "resources", "assets", "fantasy_the_end")
ENTITY_DIR = os.path.join(ASSETS_DIR, "textures", "entity", "tameable_enderman")
GUI_DIR = os.path.join(ASSETS_DIR, "textures", "gui", "container")


def create_entity_texture():
    """生成紫色主题的末影人纹理 (64x64)"""
    img = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # 颜色定义
    BODY_DARK = (30, 0, 40, 255)       # 深紫
    BODY_MAIN = (45, 5, 55, 255)       # 主紫色
    BODY_LIGHT = (60, 10, 70, 255)     # 浅紫
    EYE_COLOR = (180, 60, 255, 255)    # 亮紫眼
    MOUTH_COLOR = (100, 20, 120, 255)  # 紫色嘴
    TEETH_COLOR = (200, 150, 255, 255) # 紫白牙齿

    # === 头部 (正面, 8x8 在纹理左上角, 实际面部在 8x8 区域内) ===
    # 头: UV (0, 0) - (8, 8) 在纹理上
    # 实际头部纹理在 64x64 中位于 (0, 0) 到 (8, 8) 区域

    # 头 - 顶面
    for x in range(4, 12):
        for y in range(0, 4):
            img.putpixel((x, y), BODY_MAIN)
    # 头 - 正面 (面部)
    for x in range(4, 12):
        for y in range(4, 12):
            img.putpixel((x, y), BODY_MAIN)

    # 眼睛 (紫色发光)
    # 左眼
    for ex in range(6, 8):
        for ey in range(5, 7):
            img.putpixel((ex, ey), EYE_COLOR)
    # 右眼
    for ex in range(8, 10):
        for ey in range(5, 7):
            img.putpixel((ex, ey), EYE_COLOR)

    # 嘴巴
    for mx in range(6, 10):
        for my in range(8, 9):
            img.putpixel((mx, my), MOUTH_COLOR)

    # 牙齿
    for tx in range(6, 10):
        for ty in range(9, 10):
            if (tx + ty) % 2 == 0:
                img.putpixel((tx, ty), TEETH_COLOR)

    # 头 - 右侧面
    for x in range(12, 16):
        for y in range(4, 12):
            img.putpixel((x, y), BODY_DARK)

    # === 身体 (16x16 区域) ===
    # 身体正面 (UV 16, 0) - (32, 12)
    for x in range(16, 32):
        for y in range(0, 12):
            shade = BODY_MAIN if (x % 4 != 0) else BODY_DARK
            img.putpixel((x, y), BODY_MAIN)

    # 身体右侧面
    for x in range(32, 40):
        for y in range(0, 12):
            img.putpixel((x, y), BODY_DARK)

    # === 右臂 (52, 0) - (60, 12) ===
    for x in range(52, 60):
        for y in range(0, 12):
            img.putpixel((x, y), BODY_MAIN)

    # === 左臂 (40, 0) - (48, 12) 在纹理上 ===
    for x in range(40, 48):
        for y in range(0, 12):
            img.putpixel((x, y), BODY_MAIN)

    # === 腿 (正面) ===
    # 右腿 (4, 12) - (8, 24)
    for x in range(4, 8):
        for y in range(12, 24):
            img.putpixel((x, y), BODY_MAIN)
    # 左腿 (8, 12) - (12, 24)
    for x in range(8, 12):
        for y in range(12, 24):
            img.putpixel((x, y), BODY_MAIN)

    # === 身体正面 (下半部分, 12-16) ===
    for x in range(16, 32):
        for y in range(12, 16):
            img.putpixel((x, y), BODY_MAIN)

    # === 身体侧面 (下半部分) ===
    for x in range(32, 40):
        for y in range(12, 16):
            img.putpixel((x, y), BODY_DARK)

    # === 右臂下半部分 (52, 12) - (60, 24) ===
    for x in range(52, 60):
        for y in range(12, 24):
            img.putpixel((x, y), BODY_MAIN)

    # === 左臂下半部分 (40, 12) - (48, 24) ===
    for x in range(40, 48):
        for y in range(12, 24):
            img.putpixel((x, y), BODY_MAIN)

    # === 腿侧面 (纹理右侧) ===
    # 右腿 (0, 20) - (4, 32)
    for x in range(0, 4):
        for y in range(20, 32):
            img.putpixel((x, y), BODY_DARK)
    # 左腿 (12, 20) - (16, 32)
    for x in range(12, 16):
        for y in range(20, 32):
            img.putpixel((x, y), BODY_DARK)

    # === 覆盖层 (眼睛/嘴巴发光效果) ===
    # 这层在纹理的 (0, 32) 到 (64, 64) 区域
    # 眼睛发光 (覆盖层)
    for ex in range(6, 8):
        for ey in range(37, 39):
            img.putpixel((ex, ey), EYE_COLOR)
    for ex in range(8, 10):
        for ey in range(37, 39):
            img.putpixel((ex, ey), EYE_COLOR)

    # 嘴巴 (覆盖层)
    for mx in range(6, 10):
        for my in range(40, 41):
            img.putpixel((mx, my), MOUTH_COLOR)

    # 身体覆盖层 - 紫色光晕
    for x in range(16, 32):
        for y in range(32, 44):
            r, g, b, a = img.getpixel((x, y))
            if a > 0:
                # 添加紫色调
                img.putpixel((x, y), (min(r + 10, 255), g, min(b + 20, 255), a))

    return img


def create_angry_texture(base_img):
    """基于基础纹理创建愤怒版本 (红眼)"""
    img = base_img.copy()
    draw = ImageDraw.Draw(img)

    RED_EYE = (255, 30, 30, 255)     # 红眼
    RED_GLOW = (255, 50, 50, 180)    # 红色光晕

    # 覆盖眼睛为红色
    # 正面眼睛
    for ex in range(6, 8):
        for ey in range(5, 7):
            img.putpixel((ex, ey), RED_EYE)
    for ex in range(8, 10):
        for ey in range(5, 7):
            img.putpixel((ex, ey), RED_EYE)

    # 覆盖层眼睛
    for ex in range(6, 8):
        for ey in range(37, 39):
            img.putpixel((ex, ey), RED_EYE)
    for ex in range(8, 10):
        for ey in range(37, 39):
            img.putpixel((ex, ey), RED_EYE)

    # 嘴巴改为红色
    for mx in range(6, 10):
        for my in range(8, 9):
            img.putpixel((mx, my), (200, 20, 20, 255))
    for mx in range(6, 10):
        for my in range(40, 41):
            img.putpixel((mx, my), (200, 20, 20, 255))

    # 身体添加红色调
    for x in range(16, 32):
        for y in range(0, 16):
            r, g, b, a = img.getpixel((x, y))
            if a > 0:
                r = min(r + 15, 255)
                img.putpixel((x, y), (r, g, b, a))

    # 手臂红色调
    for x in range(40, 48):
        for y in range(0, 24):
            r, g, b, a = img.getpixel((x, y))
            if a > 0:
                r = min(r + 15, 255)
                img.putpixel((x, y), (r, g, b, a))
    for x in range(52, 60):
        for y in range(0, 24):
            r, g, b, a = img.getpixel((x, y))
            if a > 0:
                r = min(r + 15, 255)
                img.putpixel((x, y), (r, g, b, a))

    return img


def create_gui_texture():
    """生成末影人背包 GUI 纹理 (256x222)"""
    img = Image.new("RGBA", (256, 222), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # 颜色定义 - 末地紫色主题
    BG_DARK = (25, 10, 45, 255)       # 深紫背景
    BG_MID = (35, 15, 55, 255)        # 中紫
    BG_LIGHT = (45, 20, 65, 255)      # 浅紫
    BORDER = (60, 25, 80, 255)        # 边框紫
    SLOT_BG = (20, 8, 35, 255)        # 槽位背景
    SLOT_HIGHLIGHT = (50, 22, 75, 255) # 槽位高亮
    TITLE_COLOR = (180, 120, 220, 255) # 标题紫

    # 填充背景
    for x in range(256):
        for y in range(222):
            # 渐变背景
            intensity = 1.0 - (y / 222) * 0.3
            r = int(BG_DARK[0] * intensity)
            g = int(BG_DARK[1] * intensity)
            b = int(BG_DARK[2] * intensity)
            img.putpixel((x, y), (r, g, b, 255))

    # 主背包区域 (6x9) - 大致位置
    inv_left = 6
    inv_top = 16
    inv_right = inv_left + 9 * 18 + 2  # 170
    inv_bottom = inv_top + 6 * 18 + 2  # 126

    # 绘制主背包区域背景
    for x in range(inv_left, inv_right + 1):
        for y in range(inv_top, inv_bottom + 1):
            img.putpixel((x, y), BG_MID)

    # 绘制主背包边框
    draw.rectangle([inv_left, inv_top, inv_right, inv_bottom],
                   outline=BORDER, width=1)

    # 绘制槽位 (6x9)
    for row in range(6):
        for col in range(9):
            sx = 8 + col * 18
            sy = 18 + row * 18
            draw.rectangle([sx, sy, sx + 16, sy + 16],
                          fill=SLOT_BG, outline=SLOT_HIGHLIGHT, width=1)

    # 盔甲槽区域 (4个垂直)
    armor_left = 174
    armor_top = 16
    armor_right = armor_left + 18 + 2
    armor_bottom = armor_top + 4 * 18 + 2

    # 绘制盔甲槽区域背景
    for x in range(armor_left, armor_right + 1):
        for y in range(armor_top, armor_bottom + 1):
            img.putpixel((x, y), BG_MID)

    # 绘制盔甲槽边框
    draw.rectangle([armor_left, armor_top, armor_right, armor_bottom],
                   outline=BORDER, width=1)

    # 绘制盔甲槽位
    for i in range(4):
        sx = 176
        sy = 18 + i * 18
        draw.rectangle([sx, sy, sx + 16, sy + 16],
                      fill=SLOT_BG, outline=SLOT_HIGHLIGHT, width=1)

    # 玩家背包区域 (3x9 + 快捷栏)
    player_top = 138
    player_bottom = player_top + 4 * 18 + 2  # 3行背包 + 1行快捷栏

    # 绘制玩家背包区域背景
    for x in range(inv_left, inv_right + 1):
        for y in range(player_top, inv_right + 1 if False else player_bottom + 1):
            if y <= player_bottom:
                img.putpixel((x, y), BG_MID)

    # 绘制玩家背包边框
    draw.rectangle([inv_left, player_top, inv_right, player_bottom],
                   outline=BORDER, width=1)

    # 绘制玩家背包槽位 (3x9)
    for row in range(3):
        for col in range(9):
            sx = 8 + col * 18
            sy = 140 + row * 18
            draw.rectangle([sx, sy, sx + 16, sy + 16],
                          fill=SLOT_BG, outline=SLOT_HIGHLIGHT, width=1)

    # 绘制快捷栏槽位 (1x9)
    for col in range(9):
        sx = 8 + col * 18
        sy = 198
        draw.rectangle([sx, sy, sx + 16, sy + 16],
                      fill=SLOT_BG, outline=SLOT_HIGHLIGHT, width=1)

    # 分隔线
    draw.line([inv_left, 137, inv_right, 137], fill=BORDER, width=1)

    # 末影人模型区域 (左侧装饰)
    # 画个紫色光晕背景
    for x in range(0, 60):
        for y in range(0, 222):
            dist = ((x - 30) ** 2 + (y - 111) ** 2) ** 0.5
            if dist < 50:
                alpha = max(0, int(40 * (1 - dist / 50)))
                r, g, b, a = img.getpixel((x, y))
                r = min(r + 10, 255)
                g = min(g + 5, 255)
                b = min(b + 20, 255)
                img.putpixel((x, y), (r, g, b, 255))

    return img


def main():
    os.makedirs(ENTITY_DIR, exist_ok=True)
    os.makedirs(GUI_DIR, exist_ok=True)

    # 生成实体纹理
    entity_img = create_entity_texture()
    entity_path = os.path.join(ENTITY_DIR, "tameable_enderman.png")
    entity_img.save(entity_path)
    print(f"已生成: {entity_path}")

    # 生成愤怒纹理
    angry_img = create_angry_texture(entity_img)
    angry_path = os.path.join(ENTITY_DIR, "tameable_enderman_angry.png")
    angry_img.save(angry_path)
    print(f"已生成: {angry_path}")

    # 生成 GUI 纹理
    gui_img = create_gui_texture()
    gui_path = os.path.join(GUI_DIR, "ender_man.png")
    gui_img.save(gui_path)
    print(f"已生成: {gui_path}")

    print("\n所有纹理生成完成!")


if __name__ == "__main__":
    main()