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

import com.fantasy.end.network.EnderManNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 在创造模式物品栏界面添加「末影人背包」入口。
 * 点击后向服务端请求该玩家已驯服的末影人列表：
 * 仅一只时直接打开其背包，多只时弹出选择界面（列出所有已驯服末影人，可打开各自的背包）。
 */
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenEnderManButtonMixin {

    @Inject(method = "init", at = @At("RETURN"))
    private void fantasy_end_addEndermanButton(CallbackInfo ci) {
        CreativeInventoryScreen screen = (CreativeInventoryScreen) (Object) this;
        HandledScreenAccessor accessor = (HandledScreenAccessor) screen;
        ScreenAccessor screenAccessor = (ScreenAccessor) screen;
        int x = accessor.getX() + accessor.getBackgroundWidth() - 90;
        int y = accessor.getY() + 4;
        screenAccessor.invokeAddDrawableChild(ButtonWidget.builder(
                Text.literal("末影人"),
                btn -> ClientPlayNetworking.send(new EnderManNetworking.RequestEnderManListPayload())
        ).dimensions(x, y, 78, 20).build());
    }
}