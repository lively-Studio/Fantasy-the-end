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

import com.fantasy.end.entity.TameableEnderManEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.state.EndermanEntityRenderState;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

/**
 * 可驯服末影人渲染器
 * 完全复用原版末影人模型与纹理（外观与原版一致）
 * 仅驯服后抑制愤怒（眼睛不变红）状态
 */
public class TameableEnderManEntityRenderer extends EndermanEntityRenderer {

    public TameableEnderManEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        // 继承 EndermanEntityRenderer 的原版 EndermanEntityModel 与
        // 方块搬运特征、眼睛发光特征，不进行任何替换
    }

    private static final Identifier TEXTURE =
            Identifier.ofVanilla("textures/entity/enderman/enderman.png");

    @Override
    public Identifier getTexture(EndermanEntityRenderState state) {
        // 使用原版末影人贴图
        return TEXTURE;
    }

    @Override
    public void updateRenderState(EndermanEntity enderman, EndermanEntityRenderState state, float tickDelta) {
        super.updateRenderState(enderman, state, tickDelta);
        // 如果是可驯服末影人且已驯服，则不显示愤怒状态（眼睛不变红）
        if (enderman instanceof TameableEnderManEntity tameable && tameable.isTamed()) {
            state.angry = false;
        }
    }
}
