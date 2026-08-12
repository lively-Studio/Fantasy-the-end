#!/usr/bin/env python3
"""
修复 1.21.11 盔甲穿戴显示问题：
1. 复制旧 layer 纹理到 textures/entity/equipment/humanoid[/leggings] 目录
2. 生成 assets/fantasy_the_end/equipment/*.json Equipment Asset 定义
"""
import os
import shutil
import json

BASE = "/Users/cangcang/code/Fantasy- The End/src/main/resources/assets/fantasy_the_end"
OLD_ARMOR = os.path.join(BASE, "textures/models/armor")
NEW_HUMANOID = os.path.join(BASE, "textures/entity/equipment/humanoid")
NEW_HUMANOID_LEGGINGS = os.path.join(BASE, "textures/entity/equipment/humanoid_leggings")
EQUIPMENT_DIR = os.path.join(BASE, "equipment")

def copy_texture(src, dst):
    os.makedirs(os.path.dirname(dst), exist_ok=True)
    shutil.copy2(src, dst)
    print(f"[COPY] {os.path.basename(src)} -> {os.path.relpath(dst, BASE)}")

def gen_equipment(name):
    """参考原版 netherite.json 格式生成 Equipment Asset JSON"""
    data = {
        "layers": {
            "humanoid": [
                {"texture": f"fantasy_the_end:{name}"}
            ],
            "humanoid_leggings": [
                {"texture": f"fantasy_the_end:{name}"}
            ],
            # 我们的盔甲不支持马铠和鹦鹉螺，不过可以留空或者也指向同样的纹理
            "horse_body": [
                {"texture": f"fantasy_the_end:{name}"}
            ],
            "nautilus_body": [
                {"texture": f"fantasy_the_end:{name}"}
            ]
        }
    }
    path = os.path.join(EQUIPMENT_DIR, f"{name}.json")
    os.makedirs(EQUIPMENT_DIR, exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=2, ensure_ascii=False)
        f.write("\n")
    print(f"[GEN]  equipment/{name}.json")

def main():
    # === 末影盔甲 ===
    # layer_1 = 头盔 + 胸甲 + 靴子 (humanoid)
    copy_texture(
        os.path.join(OLD_ARMOR, "ender_layer_1.png"),
        os.path.join(NEW_HUMANOID, "ender.png")
    )
    # layer_2 = 护腿 (humanoid_leggings)
    copy_texture(
        os.path.join(OLD_ARMOR, "ender_layer_2.png"),
        os.path.join(NEW_HUMANOID_LEGGINGS, "ender.png")
    )
    gen_equipment("ender")

    print()

    # === 幻影盔甲 ===
    copy_texture(
        os.path.join(OLD_ARMOR, "phantom_layer_1.png"),
        os.path.join(NEW_HUMANOID, "phantom.png")
    )
    copy_texture(
        os.path.join(OLD_ARMOR, "phantom_layer_2.png"),
        os.path.join(NEW_HUMANOID_LEGGINGS, "phantom.png")
    )
    gen_equipment("phantom")

    print("\n完成！注意：")
    print("  1. 保留旧的 textures/models/armor 目录（兼容某些视觉模组）")
    print("  2. EquipmentAsset 命名空间已对应 ModArmorMaterials 中的 ENDER_TRIM_KEY / PHANTOM_TRIM_KEY")
    print("  3. 需要完全关闭 Minecraft 再启动，才能加载新的盔甲渲染。")

if __name__ == "__main__":
    main()
