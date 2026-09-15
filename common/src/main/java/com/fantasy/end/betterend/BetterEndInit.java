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
 * Fantasy: The End 是 BetterEnd (https://github.com/quiqueck/BetterEnd, MIT,
 * (c) 2020 paulevsGitch) 的二次开发（衍生作品）。本初始化器挂载所有由 BetterEnd
 * 移植而来的子系统，独立于原 Fantasy: The End 初始化器，便于 1.21.x 与 26.x 共用。
 */
package com.fantasy.end.betterend;

import com.fantasy.end.betterend.registry.BetterEndBlocks;
import com.fantasy.end.betterend.world.EndOreFeatures;
import net.fabricmc.api.ModInitializer;

/** BetterEnd 移植子系统的总入口（双版本共用的独立 ModInitializer）。 */
public class BetterEndInit implements ModInitializer {
    @Override
    public void onInitialize() {
        // 区块在静态初始化时已注册；此处把物品加入创造栏并注入末地矿石生成。
        BetterEndBlocks.registerItemGroupEntries();
        EndOreFeatures.register();
    }
}
