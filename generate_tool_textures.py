#!/usr/bin/env python3
"""
从原版下界合金工具纹理生成末影/幻影系列工具纹理
"""
from PIL import Image
import os
import colorsys

# ===== 配置 =====
# 原版下界合金纹理目录
SRC_DIR = "/tmp/mc_extract/assets/minecraft/textures/item"
# 输出目录
OUT_DIR = "/Users/cangcang/code/Fantasy- The End/src/main/resources/assets/fantasy_the_end/textures/item"

# 需要处理的工具/物品列表 (原版名, 末影名, 幻影名)
ITEMS = [
    ("netherite_sword", "ender_sword", "phantom_sword"),
    ("netherite_pickaxe", "ender_pickaxe", "phantom_pickaxe"),
    ("netherite_axe", "ender_axe", "phantom_axe"),
    ("netherite_shovel", "ender_shovel", "phantom_shovel"),
    ("netherite_hoe", "ender_hoe", "phantom_hoe"),
    ("netherite_upgrade_smithing_template", "ender_upgrade_smithing_template", "phantom_upgrade_smithing_template"),
]

# 末影青蓝色目标色 (参考 ender_stone 色调)
# HSV: ~188度(青蓝), 中等饱和度
ENDER_TARGET_HSV = (0.522, 0.299, 0.459)  # RGB(82,113,117) -> HSV
# 幻影紫色目标色 (参考 phantom_stone 色调)
# HSV: ~270度(紫), 中等饱和度
PHANTOM_TARGET_HSV = (0.775, 0.302, 0.494)  # RGB(100,88,126) -> HSV

def rgb_to_hsv(r, g, b):
    return colorsys.rgb_to_hsv(r / 255.0, g / 255.0, b / 255.0)

def hsv_to_rgb(h, s, v):
    r, g, b = colorsys.hsv_to_rgb(h, s, v)
    return (int(r * 255), int(g * 255), int(b * 255))

def recolor_image(img, target_hsv):
    """
    重新着色图像：保持亮度(V)和相对关系，替换色相(H)和饱和度(S)
    透明像素保持不变
    对于较暗的像素（材质阴影），稍微降低亮度保持对比
    """
    img = img.convert("RGBA")
    pixels = img.load()
    w, h = img.size
    target_h, target_s, target_v = target_hsv

    for y in range(h):
        for x in range(w):
            r, g, b, a = pixels[x, y]
            if a == 0:
                continue  # 跳过完全透明像素

            # 获取当前像素的 HSV
            cur_h, cur_s, cur_v = rgb_to_hsv(r, g, b)

            # 计算相对亮度因子：相对于目标基准亮度的比例
            # 下界合金纹理的平均亮度大约在 0.3-0.4 左右
            brightness_factor = cur_v / 0.35  # 归一化

            # 新的亮度：目标亮度 * 相对亮度因子，保持明暗对比
            new_v = target_v * brightness_factor
            new_v = max(0.0, min(1.0, new_v))

            # 饱和度：对于很暗或很亮的像素略微降低饱和度，保持自然
            sat_factor = 1.0
            if new_v < 0.15 or new_v > 0.85:
                sat_factor = 0.6
            new_s = target_s * sat_factor
            new_s = max(0.0, min(1.0, new_s))

            new_r, new_g, new_b = hsv_to_rgb(target_h, new_s, new_v)
            pixels[x, y] = (new_r, new_g, new_b, a)

    return img

def main():
    os.makedirs(OUT_DIR, exist_ok=True)

    for src_name, ender_name, phantom_name in ITEMS:
        src_path = os.path.join(SRC_DIR, f"{src_name}.png")
        if not os.path.exists(src_path):
            print(f"[WARN] 找不到源文件: {src_path}")
            continue

        src_img = Image.open(src_path)

        # 生成末影纹理 (青蓝色)
        ender_img = recolor_image(src_img, ENDER_TARGET_HSV)
        ender_out = os.path.join(OUT_DIR, f"{ender_name}.png")
        ender_img.save(ender_out)
        print(f"[OK] 生成末影纹理: {ender_name}.png")

        # 生成幻影纹理 (紫色)
        phantom_img = recolor_image(src_img, PHANTOM_TARGET_HSV)
        phantom_out = os.path.join(OUT_DIR, f"{phantom_name}.png")
        phantom_img.save(phantom_out)
        print(f"[OK] 生成幻影纹理: {phantom_name}.png")

    print("\n完成! 所有纹理已保存到:", OUT_DIR)

if __name__ == "__main__":
    main()
