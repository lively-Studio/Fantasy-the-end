#!/usr/bin/env python3
"""
批量生成装饰方块（楼梯、墙、栅栏、栅栏门、按钮、压力板）的资源文件
同时修复 slab blockstates 中的 type=double 模型引用错误
"""
import json
import os

BASE_DIR = "/Users/cangcang/code/Fantasy- The End/src/main/resources/assets/fantasy_the_end"

# ===== 要生成的方块列表 =====
# 末影系列
ENDER_BLOCKS = [
    "ender_stone_stairs",
    "ender_stone_wall",
    "ender_stone_fence",
    "ender_stone_fence_gate",
    "ender_stone_button",
    "ender_stone_pressure_plate",
]
# 幻影系列
PHANTOM_BLOCKS = [
    "phantom_stone_stairs",
    "phantom_stone_wall",
    "phantom_stone_fence",
    "phantom_stone_fence_gate",
    "phantom_stone_button",
    "phantom_stone_pressure_plate",
]

# 纹理映射：方块ID -> 纹理名（使用已有的 stone 纹理）
def get_texture(block_id):
    if block_id.startswith("ender"):
        return "ender_stone"
    else:
        return "phantom_stone"

def save_json(path, data):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=2, ensure_ascii=False)
        f.write("\n")

# ===== 生成楼梯 (Stairs) =====
def gen_stairs(block_id, texture):
    tex = f"fantasy_the_end:block/{texture}"
    model = f"fantasy_the_end:block/{block_id}"

    # blockstates
    bs = {
        "variants": {
            "facing=east,half=bottom,shape=straight":  {"model": model, "y": 270, "uvlock": True},
            "facing=east,half=bottom,shape=outer_right": {"model": f"{model}_outer", "y": 270, "uvlock": True},
            "facing=east,half=bottom,shape=outer_left":  {"model": f"{model}_outer", "y": 180, "uvlock": True},
            "facing=east,half=bottom,shape=inner_right": {"model": f"{model}_inner", "y": 270, "uvlock": True},
            "facing=east,half=bottom,shape=inner_left":  {"model": f"{model}_inner", "y": 180, "uvlock": True},
            "facing=south,half=bottom,shape=straight": {"model": model, "y": 180, "uvlock": True},
            "facing=south,half=bottom,shape=outer_right": {"model": f"{model}_outer", "y": 180, "uvlock": True},
            "facing=south,half=bottom,shape=outer_left":  {"model": f"{model}_outer", "y": 90, "uvlock": True},
            "facing=south,half=bottom,shape=inner_right": {"model": f"{model}_inner", "y": 180, "uvlock": True},
            "facing=south,half=bottom,shape=inner_left":  {"model": f"{model}_inner", "y": 90, "uvlock": True},
            "facing=west,half=bottom,shape=straight":  {"model": model, "y": 90, "uvlock": True},
            "facing=west,half=bottom,shape=outer_right": {"model": f"{model}_outer", "y": 90, "uvlock": True},
            "facing=west,half=bottom,shape=outer_left":  {"model": f"{model}_outer", "y": 0, "uvlock": True},
            "facing=west,half=bottom,shape=inner_right": {"model": f"{model}_inner", "y": 90, "uvlock": True},
            "facing=west,half=bottom,shape=inner_left":  {"model": f"{model}_inner", "y": 0, "uvlock": True},
            "facing=north,half=bottom,shape=straight": {"model": model, "uvlock": True},
            "facing=north,half=bottom,shape=outer_right": {"model": f"{model}_outer", "y": 0, "uvlock": True},
            "facing=north,half=bottom,shape=outer_left":  {"model": f"{model}_outer", "y": 270, "uvlock": True},
            "facing=north,half=bottom,shape=inner_right": {"model": f"{model}_inner", "y": 0, "uvlock": True},
            "facing=north,half=bottom,shape=inner_left":  {"model": f"{model}_inner", "y": 270, "uvlock": True},
            "facing=east,half=top,shape=straight":  {"model": model, "x": 180, "y": 90, "uvlock": True},
            "facing=east,half=top,shape=outer_right": {"model": f"{model}_outer", "x": 180, "y": 90, "uvlock": True},
            "facing=east,half=top,shape=outer_left":  {"model": f"{model}_outer", "x": 180, "y": 0, "uvlock": True},
            "facing=east,half=top,shape=inner_right": {"model": f"{model}_inner", "x": 180, "y": 90, "uvlock": True},
            "facing=east,half=top,shape=inner_left":  {"model": f"{model}_inner", "x": 180, "y": 0, "uvlock": True},
            "facing=south,half=top,shape=straight": {"model": model, "x": 180, "y": 0, "uvlock": True},
            "facing=south,half=top,shape=outer_right": {"model": f"{model}_outer", "x": 180, "y": 0, "uvlock": True},
            "facing=south,half=top,shape=outer_left":  {"model": f"{model}_outer", "x": 180, "y": 270, "uvlock": True},
            "facing=south,half=top,shape=inner_right": {"model": f"{model}_inner", "x": 180, "y": 0, "uvlock": True},
            "facing=south,half=top,shape=inner_left":  {"model": f"{model}_inner", "x": 180, "y": 270, "uvlock": True},
            "facing=west,half=top,shape=straight":  {"model": model, "x": 180, "y": 270, "uvlock": True},
            "facing=west,half=top,shape=outer_right": {"model": f"{model}_outer", "x": 180, "y": 270, "uvlock": True},
            "facing=west,half=top,shape=outer_left":  {"model": f"{model}_outer", "x": 180, "y": 180, "uvlock": True},
            "facing=west,half=top,shape=inner_right": {"model": f"{model}_inner", "x": 180, "y": 270, "uvlock": True},
            "facing=west,half=top,shape=inner_left":  {"model": f"{model}_inner", "x": 180, "y": 180, "uvlock": True},
            "facing=north,half=top,shape=straight": {"model": model, "x": 180, "y": 180, "uvlock": True},
            "facing=north,half=top,shape=outer_right": {"model": f"{model}_outer", "x": 180, "y": 180, "uvlock": True},
            "facing=north,half=top,shape=outer_left":  {"model": f"{model}_outer", "x": 180, "y": 90, "uvlock": True},
            "facing=north,half=top,shape=inner_right": {"model": f"{model}_inner", "x": 180, "y": 180, "uvlock": True},
            "facing=north,half=top,shape=inner_left":  {"model": f"{model}_inner", "x": 180, "y": 90, "uvlock": True}
        }
    }
    save_json(f"{BASE_DIR}/blockstates/{block_id}.json", bs)

    # 方块模型：stairs straight
    save_json(f"{BASE_DIR}/models/block/{block_id}.json", {
        "parent": "minecraft:block/stairs",
        "textures": {"bottom": tex, "top": tex, "side": tex}
    })
    # stairs inner
    save_json(f"{BASE_DIR}/models/block/{block_id}_inner.json", {
        "parent": "minecraft:block/inner_stairs",
        "textures": {"bottom": tex, "top": tex, "side": tex}
    })
    # stairs outer
    save_json(f"{BASE_DIR}/models/block/{block_id}_outer.json", {
        "parent": "minecraft:block/outer_stairs",
        "textures": {"bottom": tex, "top": tex, "side": tex}
    })

    # item model
    save_json(f"{BASE_DIR}/models/item/{block_id}.json", {
        "parent": f"fantasy_the_end:block/{block_id}"
    })

    # items/ 1.21.11 definition
    save_json(f"{BASE_DIR}/items/{block_id}.json", {
        "model": {"type": "minecraft:model", "model": f"fantasy_the_end:block/{block_id}"}
    })

# ===== 生成墙 (Wall) =====
def gen_wall(block_id, texture):
    tex = f"fantasy_the_end:block/{texture}"
    post = f"fantasy_the_end:block/{block_id}_post"
    side = f"fantasy_the_end:block/{block_id}_side"
    tall = f"fantasy_the_end:block/{block_id}_tall"

    # blockstates (multipart)
    bs = {
        "multipart": [
            {"apply": {"model": post}},
            {"when": {"north": "low"},  "apply": {"model": side, "uvlock": True}},
            {"when": {"east":  "low"},  "apply": {"model": side, "y": 90, "uvlock": True}},
            {"when": {"south": "low"},  "apply": {"model": side, "y": 180, "uvlock": True}},
            {"when": {"west":  "low"},  "apply": {"model": side, "y": 270, "uvlock": True}},
            {"when": {"north": "tall"}, "apply": {"model": tall, "uvlock": True}},
            {"when": {"east":  "tall"}, "apply": {"model": tall, "y": 90, "uvlock": True}},
            {"when": {"south": "tall"}, "apply": {"model": tall, "y": 180, "uvlock": True}},
            {"when": {"west":  "tall"}, "apply": {"model": tall, "y": 270, "uvlock": True}}
        ]
    }
    save_json(f"{BASE_DIR}/blockstates/{block_id}.json", bs)

    # 方块模型
    save_json(f"{BASE_DIR}/models/block/{block_id}_post.json", {
        "parent": "minecraft:block/template_wall_post",
        "textures": {"wall": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_side.json", {
        "parent": "minecraft:block/template_wall_side",
        "textures": {"wall": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_tall.json", {
        "parent": "minecraft:block/template_wall_side_tall",
        "textures": {"wall": tex}
    })

    # item model (wall inventory)
    save_json(f"{BASE_DIR}/models/item/{block_id}.json", {
        "parent": "minecraft:block/wall_inventory",
        "textures": {"wall": tex}
    })

    # items/ definition
    save_json(f"{BASE_DIR}/items/{block_id}.json", {
        "model": {"type": "minecraft:model", "model": f"fantasy_the_end:item/{block_id}"}
    })

# ===== 生成栅栏 (Fence) =====
def gen_fence(block_id, texture):
    tex = f"fantasy_the_end:block/{texture}"
    post = f"fantasy_the_end:block/{block_id}_post"
    side = f"fantasy_the_end:block/{block_id}_side"

    bs = {
        "multipart": [
            {"apply": {"model": post}},
            {"when": {"north": "true"}, "apply": {"model": side, "uvlock": True}},
            {"when": {"east":  "true"}, "apply": {"model": side, "y": 90, "uvlock": True}},
            {"when": {"south": "true"}, "apply": {"model": side, "y": 180, "uvlock": True}},
            {"when": {"west":  "true"}, "apply": {"model": side, "y": 270, "uvlock": True}}
        ]
    }
    save_json(f"{BASE_DIR}/blockstates/{block_id}.json", bs)

    save_json(f"{BASE_DIR}/models/block/{block_id}_post.json", {
        "parent": "minecraft:block/fence_post",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_side.json", {
        "parent": "minecraft:block/fence_side",
        "textures": {"texture": tex}
    })

    save_json(f"{BASE_DIR}/models/item/{block_id}.json", {
        "parent": "minecraft:block/fence_inventory",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/items/{block_id}.json", {
        "model": {"type": "minecraft:model", "model": f"fantasy_the_end:item/{block_id}"}
    })

# ===== 生成栅栏门 (Fence Gate) =====
def gen_fence_gate(block_id, texture):
    tex = f"fantasy_the_end:block/{texture}"
    model = f"fantasy_the_end:block/{block_id}"

    bs = {
        "variants": {
            "facing=south,in_wall=false,open=false": {"model": model, "uvlock": True},
            "facing=south,in_wall=false,open=true":  {"model": f"{model}_open", "uvlock": True},
            "facing=south,in_wall=true,open=false":  {"model": f"{model}_wall", "uvlock": True},
            "facing=south,in_wall=true,open=true":   {"model": f"{model}_wall_open", "uvlock": True},
            "facing=west,in_wall=false,open=false":  {"model": model, "y": 270, "uvlock": True},
            "facing=west,in_wall=false,open=true":   {"model": f"{model}_open", "y": 270, "uvlock": True},
            "facing=west,in_wall=true,open=false":   {"model": f"{model}_wall", "y": 270, "uvlock": True},
            "facing=west,in_wall=true,open=true":    {"model": f"{model}_wall_open", "y": 270, "uvlock": True},
            "facing=north,in_wall=false,open=false": {"model": model, "y": 180, "uvlock": True},
            "facing=north,in_wall=false,open=true":  {"model": f"{model}_open", "y": 180, "uvlock": True},
            "facing=north,in_wall=true,open=false":  {"model": f"{model}_wall", "y": 180, "uvlock": True},
            "facing=north,in_wall=true,open=true":   {"model": f"{model}_wall_open", "y": 180, "uvlock": True},
            "facing=east,in_wall=false,open=false":  {"model": model, "y": 90, "uvlock": True},
            "facing=east,in_wall=false,open=true":   {"model": f"{model}_open", "y": 90, "uvlock": True},
            "facing=east,in_wall=true,open=false":   {"model": f"{model}_wall", "y": 90, "uvlock": True},
            "facing=east,in_wall=true,open=true":    {"model": f"{model}_wall_open", "y": 90, "uvlock": True}
        }
    }
    save_json(f"{BASE_DIR}/blockstates/{block_id}.json", bs)

    save_json(f"{BASE_DIR}/models/block/{block_id}.json", {
        "parent": "minecraft:block/template_fence_gate",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_open.json", {
        "parent": "minecraft:block/template_fence_gate_open",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_wall.json", {
        "parent": "minecraft:block/template_fence_gate_wall",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_wall_open.json", {
        "parent": "minecraft:block/template_fence_gate_wall_open",
        "textures": {"texture": tex}
    })

    save_json(f"{BASE_DIR}/models/item/{block_id}.json", {
        "parent": f"fantasy_the_end:block/{block_id}"
    })
    save_json(f"{BASE_DIR}/items/{block_id}.json", {
        "model": {"type": "minecraft:model", "model": f"fantasy_the_end:block/{block_id}"}
    })

# ===== 生成按钮 (Button) =====
def gen_button(block_id, texture):
    tex = f"fantasy_the_end:block/{texture}"
    model = f"fantasy_the_end:block/{block_id}"
    pressed = f"fantasy_the_end:block/{block_id}_pressed"

    bs = {
        "variants": {
            "face=floor,facing=north,powered=false": {"model": model, "uvlock": True},
            "face=floor,facing=east,powered=false":  {"model": model, "y": 90, "uvlock": True},
            "face=floor,facing=south,powered=false": {"model": model, "y": 180, "uvlock": True},
            "face=floor,facing=west,powered=false":  {"model": model, "y": 270, "uvlock": True},
            "face=wall,facing=north,powered=false":  {"model": f"{model}_inventory", "x": 90, "uvlock": True},
            "face=wall,facing=east,powered=false":   {"model": f"{model}_inventory", "x": 90, "y": 90, "uvlock": True},
            "face=wall,facing=south,powered=false":  {"model": f"{model}_inventory", "x": 90, "y": 180, "uvlock": True},
            "face=wall,facing=west,powered=false":   {"model": f"{model}_inventory", "x": 90, "y": 270, "uvlock": True},
            "face=ceiling,facing=north,powered=false": {"model": model, "x": 180, "uvlock": True},
            "face=ceiling,facing=east,powered=false":  {"model": model, "x": 180, "y": 90, "uvlock": True},
            "face=ceiling,facing=south,powered=false": {"model": model, "x": 180, "y": 180, "uvlock": True},
            "face=ceiling,facing=west,powered=false":  {"model": model, "x": 180, "y": 270, "uvlock": True},
            "face=floor,facing=north,powered=true":  {"model": pressed, "uvlock": True},
            "face=floor,facing=east,powered=true":   {"model": pressed, "y": 90, "uvlock": True},
            "face=floor,facing=south,powered=true":  {"model": pressed, "y": 180, "uvlock": True},
            "face=floor,facing=west,powered=true":   {"model": pressed, "y": 270, "uvlock": True},
            "face=wall,facing=north,powered=true":   {"model": f"{pressed}_inventory", "x": 90, "uvlock": True},
            "face=wall,facing=east,powered=true":    {"model": f"{pressed}_inventory", "x": 90, "y": 90, "uvlock": True},
            "face=wall,facing=south,powered=true":   {"model": f"{pressed}_inventory", "x": 90, "y": 180, "uvlock": True},
            "face=wall,facing=west,powered=true":    {"model": f"{pressed}_inventory", "x": 90, "y": 270, "uvlock": True},
            "face=ceiling,facing=north,powered=true": {"model": pressed, "x": 180, "uvlock": True},
            "face=ceiling,facing=east,powered=true":  {"model": pressed, "x": 180, "y": 90, "uvlock": True},
            "face=ceiling,facing=south,powered=true": {"model": pressed, "x": 180, "y": 180, "uvlock": True},
            "face=ceiling,facing=west,powered=true":  {"model": pressed, "x": 180, "y": 270, "uvlock": True}
        }
    }
    save_json(f"{BASE_DIR}/blockstates/{block_id}.json", bs)

    save_json(f"{BASE_DIR}/models/block/{block_id}.json", {
        "parent": "minecraft:block/button",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_pressed.json", {
        "parent": "minecraft:block/button_pressed",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_inventory.json", {
        "parent": "minecraft:block/button",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_pressed_inventory.json", {
        "parent": "minecraft:block/button_pressed",
        "textures": {"texture": tex}
    })

    save_json(f"{BASE_DIR}/models/item/{block_id}.json", {
        "parent": "minecraft:block/button_inventory",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/items/{block_id}.json", {
        "model": {"type": "minecraft:model", "model": f"fantasy_the_end:item/{block_id}"}
    })

# ===== 生成压力板 (Pressure Plate) =====
def gen_pressure_plate(block_id, texture):
    tex = f"fantasy_the_end:block/{texture}"
    model = f"fantasy_the_end:block/{block_id}"
    down = f"fantasy_the_end:block/{block_id}_down"

    bs = {
        "variants": {
            "powered=false": {"model": model},
            "powered=true":  {"model": down}
        }
    }
    save_json(f"{BASE_DIR}/blockstates/{block_id}.json", bs)

    save_json(f"{BASE_DIR}/models/block/{block_id}.json", {
        "parent": "minecraft:block/pressure_plate_up",
        "textures": {"texture": tex}
    })
    save_json(f"{BASE_DIR}/models/block/{block_id}_down.json", {
        "parent": "minecraft:block/pressure_plate_down",
        "textures": {"texture": tex}
    })

    save_json(f"{BASE_DIR}/models/item/{block_id}.json", {
        "parent": f"fantasy_the_end:block/{block_id}"
    })
    save_json(f"{BASE_DIR}/items/{block_id}.json", {
        "model": {"type": "minecraft:model", "model": f"fantasy_the_end:block/{block_id}"}
    })

# ===== 修复 slab blockstates =====
def fix_slab_blockstates():
    """修复 ender/phantom slab 和 stone_slab 中 type=double 引用错误的模型"""
    slabs = [
        ("ender_slab", "ender_ore"),
        ("phantom_slab", "phantom_ore"),
        ("ender_stone_slab", "ender_stone"),
        ("phantom_stone_slab", "phantom_stone"),
    ]
    for slab_id, full_block in slabs:
        path = f"{BASE_DIR}/blockstates/{slab_id}.json"
        if not os.path.exists(path):
            print(f"[SKIP] 找不到: {path}")
            continue
        with open(path, "r") as f:
            data = json.load(f)
        # 修复 type=double
        if "variants" in data and "type=double" in data["variants"]:
            data["variants"]["type=double"] = {"model": f"fantasy_the_end:block/{full_block}"}
            save_json(path, data)
            print(f"[FIX] 修复 {slab_id}.json 中 type=double -> {full_block}")

# ===== 生成一个系列的所有装饰方块 =====
def gen_decorative(block_list):
    for bid in block_list:
        tex = get_texture(bid)
        print(f"[GEN] {bid} (使用纹理: {tex})")
        if bid.endswith("_stairs"):
            gen_stairs(bid, tex)
        elif bid.endswith("_wall"):
            gen_wall(bid, tex)
        elif bid.endswith("_fence_gate"):
            gen_fence_gate(bid, tex)
        elif bid.endswith("_fence"):
            gen_fence(bid, tex)
        elif bid.endswith("_button"):
            gen_button(bid, tex)
        elif bid.endswith("_pressure_plate"):
            gen_pressure_plate(bid, tex)

def main():
    print("=== 生成末影系列装饰方块 ===")
    gen_decorative(ENDER_BLOCKS)
    print("\n=== 生成幻影系列装饰方块 ===")
    gen_decorative(PHANTOM_BLOCKS)
    print("\n=== 修复 slab blockstates ===")
    fix_slab_blockstates()
    print("\n完成!")

if __name__ == "__main__":
    main()
