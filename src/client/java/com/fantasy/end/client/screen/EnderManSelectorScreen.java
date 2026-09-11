package com.fantasy.end.client.screen;

import com.fantasy.end.network.EnderManNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

/**
 * 多只已驯服末影人时的选择界面：每只一个按钮，点击打开其背包
 */
public class EnderManSelectorScreen extends Screen {

    private final int[] ids;
    private final String[] names;

    public EnderManSelectorScreen(int[] ids, String[] names) {
        super(Text.literal("选择末影人"));
        this.ids = ids;
        this.names = names;
    }

    @Override
    protected void init() {
        GridWidget grid = new GridWidget();
        grid.getMainPositioner().margin(4);
        GridWidget.Adder adder = grid.createAdder(1);

        for (int i = 0; i < ids.length; i++) {
            final int entityId = ids[i];
            String label = (i < names.length && names[i] != null && !names[i].isBlank())
                    ? names[i] : "末影人 #" + entityId;
            adder.add(ButtonWidget.builder(
                    Text.literal(label),
                    btn -> {
                        ClientPlayNetworking.send(new EnderManNetworking.OpenEnderManPackPayload(entityId));
                        if (this.client != null) this.client.setScreen(null);
                    }
            ).width(180).build());
        }

        adder.add(ButtonWidget.builder(
                ScreenTexts.CANCEL,
                btn -> this.close()
        ).width(180).build());

        grid.setPosition((this.width - 180) / 2, this.height / 2 - ids.length * 12);
        grid.forEachChild(this::addDrawableChild);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}