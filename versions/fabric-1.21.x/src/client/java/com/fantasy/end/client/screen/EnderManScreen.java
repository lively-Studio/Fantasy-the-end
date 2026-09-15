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

package com.fantasy.end.client.screen;

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.entity.TameableEnderManEntity;
import com.fantasy.end.screen.EnderManScreenHandler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * 末影人背包 GUI 屏幕
 * 左侧显示末影人 3D 模型，右侧显示 54 格背包 + 盔甲槽 + 玩家背包
 */
public class EnderManScreen extends HandledScreen<EnderManScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of(FantasyTheEnd.MOD_ID, "textures/gui/container/ender_man.png");

    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 222;

    // 末影人模型绘制位置
    private static final int MODEL_X = 33;
    private static final int MODEL_Y = 55;
    private static final int MODEL_SIZE = 60;

    private TameableEnderManEntity enderman;
    private float mouseX = 0;
    private float mouseY = 0;

    public EnderManScreen(EnderManScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = TEXTURE_WIDTH;
        this.backgroundHeight = TEXTURE_HEIGHT;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        // 尝试从客户端世界获取末影人实体
        // 使用 Property 同步的实体 ID（从服务端同步到客户端）
        if (this.client != null && this.client.world != null) {
            int entityId = this.handler.getEntityId();
            if (entityId > 0) {
                var entity = this.client.world.getEntityById(entityId);
                if (entity instanceof TameableEnderManEntity tameable) {
                    this.enderman = tameable;
                }
            }
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;

        // 绘制背景纹理
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0f, 0.0f, TEXTURE_WIDTH, TEXTURE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        // 绘制末影人 3D 模型
        if (this.enderman != null) {
            int modelDrawX = x + MODEL_X;
            int modelDrawY = y + MODEL_Y;

            InventoryScreen.drawEntity(
                    context,
                    modelDrawX,
                    modelDrawY,
                    MODEL_SIZE,
                    (int) this.mouseX - modelDrawX,
                    (int) this.mouseY - modelDrawY,
                    0.0f,
                    0.0f,
                    0.0f,
                    this.enderman
            );
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        // 绘制标题
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0x404040, false);
        // 绘制"背包"标签
        context.drawText(this.textRenderer, this.playerInventoryTitle, 8, this.playerInventoryTitleY, 0x404040, false);
    }
}