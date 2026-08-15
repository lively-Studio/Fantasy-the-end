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
package com.fantasy.end.client.mixin;

import com.fantasy.end.item.BackpackItem;
import com.fantasy.end.network.BackpackNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class BackpackClickMixin {

    @Unique
    private long lastClickTime = 0;

    @Unique
    private int lastClickSlot = -1;

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        // 仅在玩家背包界面处理双击
        if (!(screen instanceof InventoryScreen)) return;

        Slot slot = ((HandledScreenAccessor) screen).invokeGetSlotAt(mouseX, mouseY);
        if (slot == null) return;

        if (slot.hasStack() && slot.getStack().getItem() instanceof BackpackItem) {
            long now = System.currentTimeMillis();
            if (now - lastClickTime < 300 && slot.id == lastClickSlot) {
                // 双击检测到，发送网络包到服务器打开背包
                cir.setReturnValue(true);
                ClientPlayNetworking.send(new BackpackNetworking.OpenBackpackPayload(slot.id));
                return;
            }
            lastClickTime = now;
            lastClickSlot = slot.id;
        }
    }
}