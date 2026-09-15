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

package com.fantasy.end.network;

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.item.BackpackItem;
import com.fantasy.end.screen.BackpackScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class BackpackNetworking {

    public record OpenBackpackPayload(int slotIndex) implements CustomPayload {
        public static final CustomPayload.Id<OpenBackpackPayload> ID =
                new CustomPayload.Id<>(Identifier.of(FantasyTheEnd.MOD_ID, "open_backpack"));

        public static final PacketCodec<PacketByteBuf, OpenBackpackPayload> CODEC = PacketCodec.of(
                (value, buf) -> buf.writeInt(value.slotIndex()),
                buf -> new OpenBackpackPayload(buf.readInt())
        );

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public static void init() {
        PayloadTypeRegistry.playC2S().register(OpenBackpackPayload.ID, OpenBackpackPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(OpenBackpackPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                PlayerEntity player = context.player();
                int slotIndex = payload.slotIndex();

                if (slotIndex >= 0 && slotIndex < player.currentScreenHandler.slots.size()) {
                    ItemStack stack = player.currentScreenHandler.getSlot(slotIndex).getStack();
                    if (stack.getItem() instanceof BackpackItem) {
                        Text title = stack.getName();
                        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                                (syncId, inv, p) -> new BackpackScreenHandler(syncId, inv, stack),
                                title
                        ));
                    }
                }
            });
        });
    }

    private BackpackNetworking() {
    }
}