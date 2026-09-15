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

package com.fantasy.end.client.entity.renderer;

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.client.entity.model.TameableEnderManEntityModel;
import com.fantasy.end.registry.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
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

        // 注册自定义实体渲染器（用于刷蛋/命令生成的可驯服末影人）
        EntityRendererRegistry.register(
                ModEntities.TAMEABLE_ENDER_MAN,
                TameableEnderManEntityRenderer::new
        );

        // 关键：为原版末影人类型注册自定义渲染器
        // 这样通过 EntityFactoryMixin 创建的 TameableEnderManEntity（使用 EntityType.ENDERMAN）
        // 也能使用自定义纹理和模型
        EntityRendererRegistry.register(
                EntityType.ENDERMAN,
                TameableEnderManEntityRenderer::new
        );

        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册实体渲染器完成。");
    }
}
