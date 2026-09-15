/*
 * MIT License
 *
 * Copyright (c) 2026 lively-Studio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * ----------------------------------------------------------------------------
 * 派生声明 / Derivative notice:
 * 本文件改编自开源模组 BetterEnd (https://github.com/quiqueck/BetterEnd)，
 * BetterEnd 本身以 MIT 许可证发布 (Copyright (c) 2020 paulevsGitch)。
 * Fantasy: The End 是 BetterEnd 的二次开发（衍生作品）；本文件保留 BetterEnd
 * 的设计思路，并以 Fabric API 重新实现，以求在 1.21.x 与 26.x 双版本通用。
 */
package com.fantasy.end.betterend.block;

import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;

/**
 * 末地硫铜矿（Thallasium Ore）——改编自 BetterEnd 的同名末地矿石。
 * 使用原版 {@link OreBlock} 实现，紫色调、需镐开采，掉落自身。
 */
public class ThallasiumOreBlock extends OreBlock {
    public ThallasiumOreBlock() {
        super(Block.Settings.create()
                .mapColor(MapColor.PURPLE)
                .requiresTool()
                .strength(3.0F, 3.0F)
                .sounds(BlockSoundGroup.STONE));
    }
}
