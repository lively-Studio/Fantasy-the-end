#!/usr/bin/env python3
"""
基于原版下界合金盔甲纹理，生成末影(青蓝)和幻影(紫色)的盔甲纹理。

生成内容：
1. 盔甲渲染层纹理（穿戴在玩家模型上显示）
   - humanoid/ender.png, phantom.png (头盔/胸甲/靴子)
   - humanoid_leggings/ender.png, phantom.png (护腿)
2. 盔甲物品纹理（背包/快捷栏图标）
   - ender/phantom_helmet.png, _chestplate.png, _leggings.png, _boots.png
"""
from PIL import Image
import os
import colorsys

SRC = "/tmp/mc_extract/assets/minecraft"
MOD = "/Users/cangcang/code/Fantasy- The End/src/main/resources/assets/fantasy_the_end"

# 末影青蓝 / 幻影紫 色调（和工具纹理一致）
ENDER_HSV = (0.522, 0.299, 0.459)
PHANTOM_HSV = (0.775, 0.302, 0.494)

def rgb_to_hsv01(r, g, b):
    return colorsys.rgb_to_hsv(r / 255.0, g / 255.0, b / 255.0)

def hsv_to_rgb01(h, s, v):
    r, g, b = colorsys.hsv_to_rgb(h, s, v)
    return (int(r * 255), int(g * 255), int(b * 255))

def recolor_armor_texture(img, target_hsv, brightness_base=0.5):
    """保持原始明暗对比的色相替换，专门用于P模式(调色板)纹理"""
    img = img.convert("RGBA")
    px = img.load()
    w, h = img.size
    th, ts, tv = target_hsv

    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]
            if a == 0:
                continue
            # 计算原始亮度因子
            ch, cs, cv = rgb_to_hsv01(r, g, b)
            factor = cv / brightness_base if brightness_base > 0 else 1.0
            new_v = max(0.0, min(1.0, tv * factor))
            # 饱和度：保留原图饱和度变化，但映射到目标饱和度
            new_s = ts * (0.3 + 0.7 * (cs / 0.5 if cs > 0 else 0.5))
            new_s = max(0.05, min(1.0, new_s))
            nr, ng, nb = hsv_to_rgb01(th, new_s, new_v)
            px[x, y] = (nr, ng, nb, a)
    return img


def main():
    # ===== 1. 盔甲渲染层纹理 =====
    render_sources = [
        ("humanoid", "netherite.png"),       # 胸甲/头盔/靴子
        ("humanoid_leggings", "netherite.png"),  # 护腿
    ]
    variants = [
        ("ender", ENDER_HSV),
        ("phantom", PHANTOM_HSV),
    ]

    for subdir, src_file in render_sources:
        src = os.path.join(SRC, "textures/entity/equipment", subdir, src_file)
        src_img = Image.open(src)

        for variant_name, hsv in variants:
            out_dir = os.path.join(MOD, "textures/entity/equipment", subdir)
            os.makedirs(out_dir, exist_ok=True)
            out_path = os.path.join(out_dir, f"{variant_name}.png")

            # 重新着色
            recolored = recolor_armor_texture(src_img.copy(), hsv)
            recolored.save(out_path)
            print(f"[OK] textures/entity/equipment/{subdir}/{variant_name}.png ({os.path.getsize(out_path)} bytes)")

    # ===== 2. 盔甲物品纹理 =====
    armor_pieces = ["helmet", "chestplate", "leggings", "boots"]

    for piece in armor_pieces:
        src = os.path.join(SRC, "textures/item", f"netherite_{piece}.png")
        src_img = Image.open(src)

        for variant_name, hsv in variants:
            out_dir = os.path.join(MOD, "textures/item")
            os.makedirs(out_dir, exist_ok=True)
            out_path = os.path.join(out_dir, f"{variant_name}_{piece}.png")

            recolored = recolor_armor_texture(src_img.copy(), hsv, brightness_base=0.5)
            recolored.save(out_path)
            print(f"[OK] textures/item/{variant_name}_{piece}.png ({os.path.getsize(out_path)} bytes)")

    print("\n全部盔甲纹理生成完成！")
    print("源：原版下界合金盔甲纹理")
    print("末影系列：青蓝色调")
    print("幻影系列：紫色调")


if __name__ == "__main__":
    main()