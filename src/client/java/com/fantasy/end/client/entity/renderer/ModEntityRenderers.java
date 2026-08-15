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
package com.fantasy.end.client.entity.renderer;

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.client.entity.model.TameableEnderManEntityModel;
import com.fantasy.end.registry.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/**
 * 客户端实体渲染器和模型层注册
 */
public class ModEntityRenderers {

    public static final EntityModelLayer TAMEABLE_ENDER_MAN_LAYER =
            new EntityModelLayer(Identifier.of(FantasyTheEnd.MOD_ID, "tameable_enderman"), "main");

    public static void init() {
        // 注册模型层
        EntityModelLayerRegistry.registerModelLayer(
                TAMEABLE_ENDER_MAN_LAYER,
                TameableEnderManEntityModel::getTexturedModelData
        );

        // 注册实体渲染器
        EntityRendererRegistry.register(
                ModEntities.TAMEABLE_ENDER_MAN,
                TameableEnderManEntityRenderer::new
        );

        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册实体渲染器完成。");
    }
}