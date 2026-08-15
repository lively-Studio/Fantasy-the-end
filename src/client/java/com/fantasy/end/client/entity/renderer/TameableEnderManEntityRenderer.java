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
import com.fantasy.end.entity.TameableEnderManEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.feature.EndermanBlockFeatureRenderer;
import net.minecraft.client.render.entity.state.EndermanEntityRenderState;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

/**
 * 可驯服末影人渲染器
 * 复用原版末影人渲染逻辑，替换纹理
 */
public class TameableEnderManEntityRenderer extends EndermanEntityRenderer {

    private static final Identifier TEXTURE = Identifier.of(FantasyTheEnd.MOD_ID, "textures/entity/tameable_enderman/tameable_enderman.png");
    private static final Identifier ANGRY_TEXTURE = Identifier.of(FantasyTheEnd.MOD_ID, "textures/entity/tameable_enderman/tameable_enderman_angry.png");

    public TameableEnderManEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        // 替换模型为我们的模型
        this.model = new TameableEnderManEntityModel(
                context.getPart(ModEntityRenderers.TAMEABLE_ENDER_MAN_LAYER)
        );
        // 移除方块搬运特征渲染（驯服末影人默认不搬运方块）
        this.features.removeIf(feature -> feature instanceof EndermanBlockFeatureRenderer);
    }

    @Override
    public Identifier getTexture(EndermanEntityRenderState state) {
        return state.angry ? ANGRY_TEXTURE : TEXTURE;
    }

    @Override
    public void updateRenderState(EndermanEntity enderman, EndermanEntityRenderState state, float tickDelta) {
        super.updateRenderState(enderman, state, tickDelta);
        // 驯服后不愤怒（眼睛不变红）
        if (enderman instanceof TameableEnderManEntity tameable && tameable.isTamed()) {
            state.angry = false;
        }
    }
}