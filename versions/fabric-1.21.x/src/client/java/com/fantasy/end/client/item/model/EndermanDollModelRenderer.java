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

package com.fantasy.end.client.item.model;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.Identifier;
import org.joml.Vector3fc;

import java.util.function.Consumer;

/**
 * 末影人玩偶的 3D 内置渲染（1.21.11 新版 SpecialItemModel 体系）。
 *
 * 直接复用原版末影人模型 ({@link EntityModelLayers#ENDERMAN}) 和原版末影人纹理，
 * 渲染时整体缩小成一个"小玩偶"。
 *
 * 可微调参数（都在 {@link Impl} 中）：
 *  - SCALE    玩偶整体比例（越小越迷你）
 *  - Y_OFFSET 上下偏移（正/负调整玩偶在物品图标里的高低）
 */
public final class EndermanDollModelRenderer {

    private EndermanDollModelRenderer() {
    }

    // 用于数据文件 assets/.../items/ender_man_doll.json 中 "type": "fantasy_the_end:enderman_doll"
    public record Unbaked() implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<Unbaked> CODEC = MapCodec.unit(new Unbaked());

        @Override
        public MapCodec<Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakeContext context) {
            ModelPart root = context.entityModelSet().getModelPart(EntityModelLayers.ENDERMAN);
            return new Impl(root);
        }
    }

    static final class Impl implements SimpleSpecialModelRenderer {

        private static final Identifier ENDERMAN_TEXTURE =
                Identifier.ofVanilla("textures/entity/enderman/enderman.png");

        // ===== 可微调参数 =====
        private static final float SCALE = 0.45F;   // 玩偶整体比例
        private static final float Y_OFFSET = -0.30F; // 上下偏移

        private final ModelPart root;

        Impl(ModelPart root) {
            this.root = root;
        }

        @Override
        public void render(ItemDisplayContext displayContext, MatrixStack matrices,
                           OrderedRenderCommandQueue queue, int light, int overlay,
                           boolean glint, int i) {
            matrices.push();
            matrices.translate(0.5F, 0.5F + Y_OFFSET, 0.5F);
            matrices.scale(SCALE, -SCALE, -SCALE); // 缩小 + 翻转为实体坐标
            queue.submitModelPart(
                    this.root,
                    matrices,
                    RenderLayers.entityCutoutNoCull(ENDERMAN_TEXTURE),
                    light,
                    overlay,
                    null,
                    false,
                    glint,
                    -1,
                    null,
                    i
            );
            matrices.pop();
        }

        @Override
        public void collectVertices(Consumer<Vector3fc> consumer) {
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.translate(0.5F, 0.5F + Y_OFFSET, 0.5F);
            matrixStack.scale(SCALE, -SCALE, -SCALE);
            this.root.collectVertices(matrixStack, consumer);
        }
    }
}