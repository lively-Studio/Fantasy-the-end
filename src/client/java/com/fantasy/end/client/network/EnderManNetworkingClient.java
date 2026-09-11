package com.fantasy.end.client.network;

import com.fantasy.end.client.screen.EnderManSelectorScreen;
import com.fantasy.end.network.EnderManNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/**
 * 末影人背包相关客户端网络接收(S2C)。
 * 收到服务端返回的已驯服末影人列表后，按数量打开对应界面。
 */
public final class EnderManNetworkingClient {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(
                EnderManNetworking.EnderManListResponsePayload.ID,
                (payload, context) -> {
                    MinecraftClient client = context.client();
                    client.execute(() -> handleListPayload(payload, client));
                }
        );
    }

    private static void handleListPayload(EnderManNetworking.EnderManListResponsePayload payload, MinecraftClient client) {
        int[] ids = payload.ids();
        if (ids == null || ids.length == 0) {
            if (client.player != null) {
                client.player.sendMessage(Text.literal("你还没有已驯服的末影人。"), false);
            }
            return;
        }
        if (ids.length == 1) {
            // 仅有唯一一只：直接打开其背包
            ClientPlayNetworking.send(new EnderManNetworking.OpenEnderManPackPayload(ids[0]));
            return;
        }
        // 多只：弹出选择界面
        client.setScreen(new EnderManSelectorScreen(ids, payload.names()));
    }

    private EnderManNetworkingClient() {
    }
}