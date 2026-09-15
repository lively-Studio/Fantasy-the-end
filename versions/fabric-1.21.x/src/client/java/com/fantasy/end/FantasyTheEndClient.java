/*
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
 */

package com.fantasy.end;

import com.fantasy.end.client.entity.renderer.ModEntityRenderers;
import com.fantasy.end.client.screen.BackpackScreen;
import com.fantasy.end.client.screen.ModScreens;
import com.fantasy.end.registry.ModBlocks;
import com.fantasy.end.registry.ModDecorativeBlocks;
import com.fantasy.end.registry.ModScreenHandlers;
import com.fantasy.mob.client.MobGuiButton;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.text.Text;

public class FantasyTheEndClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(ModBlocks.ENDER_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.PHANTOM_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.ENDER_TRAPDOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.PHANTOM_TRAPDOOR, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ENDER_STONE_BUTTON, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PHANTOM_STONE_BUTTON, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ENDER_STONE_FENCE_GATE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PHANTOM_STONE_FENCE_GATE, BlockRenderLayer.CUTOUT);

        HandledScreens.register(ModScreenHandlers.BACKPACK, BackpackScreen::new);

        // 注册实体渲染器和模型层
        ModEntityRenderers.init();

        // 注册末影人背包屏幕
        ModScreens.init();

        // 通过 fantasy_mob 库模组在生存/创造物品栏显示「末影人背包」入口
        MobGuiButton.setText(Text.literal("末影人背包"));
        MobGuiButton.setVisible(true);

        FantasyTheEnd.LOGGER.info("[幻想:末地] 客户端初始化完成。");
    }
}
