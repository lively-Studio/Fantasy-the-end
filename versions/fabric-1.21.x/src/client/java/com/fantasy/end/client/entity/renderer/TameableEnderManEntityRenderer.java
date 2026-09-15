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
