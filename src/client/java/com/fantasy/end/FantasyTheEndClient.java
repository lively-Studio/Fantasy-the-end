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
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class FantasyTheEndClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(ModBlocks.ENDER_STONE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.putBlock(ModBlocks.PHANTOM_STONE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.putBlock(ModBlocks.ENDER_STONE_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.putBlock(ModBlocks.PHANTOM_STONE_TRAPDOOR, RenderLayer.getCutout());

        FantasyTheEnd.LOGGER.info("[幻想:末地] 客户端初始化完成。");
    }
}
