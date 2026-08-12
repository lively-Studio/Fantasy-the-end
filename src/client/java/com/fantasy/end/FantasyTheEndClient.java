/*
 * Copyright (C) 2026 cangcang
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.fantasy.end;

import com.fantasy.end.registry.ModBlocks;
import com.fantasy.end.registry.ModDecorativeBlocks;
import com.fantasy.end.registry.ModPlants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class FantasyTheEndClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(ModBlocks.ENDER_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.PHANTOM_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.ENDER_TRAPDOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.PHANTOM_TRAPDOOR, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModPlants.ENDER_FLOWER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModPlants.PHANTOM_FLOWER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModPlants.CRYSTAL_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModPlants.ENDER_VINE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ENDER_STONE_BUTTON, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PHANTOM_STONE_BUTTON, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ENDER_STONE_FENCE_GATE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PHANTOM_STONE_FENCE_GATE, BlockRenderLayer.CUTOUT);

        FantasyTheEnd.LOGGER.info("[幻想:末地] 客户端初始化完成。");
    }
}
