package com.fantasy.end.client.mixin;

import com.fantasy.end.network.EnderManNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 在玩家物品栏(E键)界面的配方书旁添加「末影人背包」按钮。
 * 点击后向服务端请求该玩家绑定的末影人列表，远程打开末影人背包。
 */
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenEnderManButtonMixin {

    @Inject(method = "init", at = @At("RETURN"))
    private void addEndermanButton(CallbackInfo ci) {
        InventoryScreen screen = (InventoryScreen) (Object) this;
        HandledScreenAccessor accessor = (HandledScreenAccessor) screen;
        ScreenAccessor screenAccessor = (ScreenAccessor) screen;
        // 配方书按钮位于背景右上角；末影人背包按钮放在其下方一行
        int x = accessor.getX() + accessor.getBackgroundWidth() - 80;
        int y = accessor.getY() + 5;
        screenAccessor.invokeAddDrawableChild(ButtonWidget.builder(
                Text.literal("末影人背包"),
                btn -> ClientPlayNetworking.send(new EnderManNetworking.RequestEnderManListPayload())
        ).dimensions(x, y, 78, 20).build());
    }
}