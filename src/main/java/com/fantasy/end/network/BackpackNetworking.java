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