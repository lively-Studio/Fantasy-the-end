#!/usr/bin/env python3
"""
基于原版纹理生成末地生态/食物/陨石纹理
- ender_fruit: 紫颂果 -> 青蓝色
- phantom_fruit: 紫颂果 -> 紫色
- roasted_chorus_fruit: 紫颂果 -> 烤制暖色
- ender_pie: 南瓜派 -> 青蓝色
- end_meteorite: 末地石 -> 深色陨石质感
"""
from PIL import Image, ImageEnhance
import os
import colorsys

SRC = "/tmp/mc_extract/assets/minecraft/textures"
OUT_ITEM = "/Users/cangcang/code/Fantasy- The End/src/main/resources/assets/fantasy_the_end/textures/item"
OUT_BLOCK = "/Users/cangcang/code/Fantasy- The End/src/main/resources/assets/fantasy_the_end/textures/block"

# 末影青蓝 / 幻影紫 色调（和之前工具纹理一致）
ENDER_HSV = (0.522, 0.299, 0.459)
PHANTOM_HSV = (0.775, 0.302, 0.494)

def rgb_to_hsv(r, g, b):
    return colorsys.rgb_to_hsv(r / 255.0, g / 255.0, b / 255.0)

def hsv_to_rgb(h, s, v):
    r, g, b = colorsys.hsv_to_rgb(h, s, v)
    return (int(r * 255), int(g * 255), int(b * 255))

def recolor(img, target_hsv, brightness_base=0.35):
    """保持明暗对比的色相替换"""
    img = img.convert("RGBA")
    px = img.load()
    w, h = img.size
    th, ts, tv = target_hsv
    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]
            if a == 0:
                continue
            ch, cs, cv = rgb_to_hsv(r, g, b)
            factor = cv / brightness_base
            new_v = max(0.0, min(1.0, tv * factor))
            new_s = ts
            if new_v < 0.15 or new_v > 0.85:
                new_s = ts * 0.6
            new_s = max(0.0, min(1.0, new_s))
            nr, ng, nb = hsv_to_rgb(th, new_s, new_v)
            px[x, y] = (nr, ng, nb, a)
    return img

def darken_and_tint(img, tint_hsv, darken=0.5, sat_boost=1.2):
    """加深并染色，做陨石质感"""
    img = img.convert("RGBA")
    px = img.load()
    w, h = img.size
    th, ts, tv = tint_hsv
    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]
            if a == 0:
                continue
            ch, cs, cv = rgb_to_hsv(r, g, b)
            new_v = cv * darken
            new_s = min(1.0, ts * sat_boost)
            nr, ng, nb = hsv_to_rgb(th, new_s, new_v)
            px[x, y] = (nr, ng, nb, a)
    return img

def warm_roast(img):
    """烤制效果：向暖色偏移"""
    img = img.convert("RGBA")
    px = img.load()
    w, h = img.size
    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]
            if a == 0:
                continue
            # 增加红黄，降低蓝
            r = min(255, int(r * 1.3 + 20))
            g = min(255, int(g * 1.1 + 10))
            b = max(0, int(b * 0.6))
            px[x, y] = (r, g, b, a)
    return img

def main():
    os.makedirs(OUT_ITEM, exist_ok=True)
    os.makedirs(OUT_BLOCK, exist_ok=True)

    # 末影果实：紫颂果 -> 青蓝
    src = Image.open(f"{SRC}/item/chorus_fruit.png")
    recolor(src, ENDER_HSV).save(f"{OUT_ITEM}/ender_fruit.png")
    print("[OK] ender_fruit.png")

    # 幻影果实：紫颂果 -> 紫色
    recolor(src, PHANTOM_HSV).save(f"{OUT_ITEM}/phantom_fruit.png")
    print("[OK] phantom_fruit.png")

    # 烤紫颂果：紫颂果 -> 烤制暖色
    warm_roast(src).save(f"{OUT_ITEM}/roasted_chorus_fruit.png")
    print("[OK] roasted_chorus_fruit.png")

    # 末影派：南瓜派 -> 青蓝
    src_pie = Image.open(f"{SRC}/item/pumpkin_pie.png")
    recolor(src_pie, ENDER_HSV, brightness_base=0.45).save(f"{OUT_ITEM}/ender_pie.png")
    print("[OK] ender_pie.png")

    # 陨石方块：末地石 -> 深色陨石
    src_stone = Image.open(f"{SRC}/block/end_stone.png")
    darken_and_tint(src_stone, ENDER_HSV, darken=0.45, sat_boost=1.4).save(f"{OUT_BLOCK}/end_meteorite.png")
    print("[OK] end_meteorite.png (block)")

    print("\n完成!")

if __name__ == "__main__":
    main()
