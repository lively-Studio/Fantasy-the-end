package com.fantasy.end.network;

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.entity.TameableEnderManEntity;
import com.fantasy.end.screen.EnderManScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 末影人背包(按E物品栏按钮) 相关网络包与服务端逻辑
 */
public final class EnderManNetworking {

    // ========== C2S: 请求打开玩家绑定的末影人列表 ==========
    public record RequestEnderManListPayload() implements CustomPayload {
        public static final CustomPayload.Id<RequestEnderManListPayload> ID =
                new CustomPayload.Id<>(Identifier.of(FantasyTheEnd.MOD_ID, "request_enderman_list"));
        public static final PacketCodec<PacketByteBuf, RequestEnderManListPayload> CODEC =
                PacketCodec.unit(new RequestEnderManListPayload());
        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    // ========== S2C: 返回玩家绑定的末影人 id 与名称列表 ==========
    public record EnderManListResponsePayload(int[] ids, String[] names) implements CustomPayload {
        public static final CustomPayload.Id<EnderManListResponsePayload> ID =
                new CustomPayload.Id<>(Identifier.of(FantasyTheEnd.MOD_ID, "enderman_list"));
        public static final PacketCodec<PacketByteBuf, EnderManListResponsePayload> CODEC = PacketCodec.of(
                (value, buf) -> {
                    buf.writeIntArray(value.ids());
                    buf.writeVarInt(value.names().length);
                    for (String n : value.names()) buf.writeString(n);
                },
                buf -> {
                    int[] ids = buf.readIntArray();
                    int len = buf.readVarInt();
                    String[] names = new String[len];
                    for (int i = 0; i < len; i++) names[i] = buf.readString();
                    return new EnderManListResponsePayload(ids, names);
                }
        );
        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    // ========== C2S: 打开指定末影人的背包 ==========
    public record OpenEnderManPackPayload(int entityId) implements CustomPayload {
        public static final CustomPayload.Id<OpenEnderManPackPayload> ID =
                new CustomPayload.Id<>(Identifier.of(FantasyTheEnd.MOD_ID, "open_enderman_pack"));
        public static final PacketCodec<PacketByteBuf, OpenEnderManPackPayload> CODEC = PacketCodec.of(
                (value, buf) -> buf.writeInt(value.entityId()),
                buf -> new OpenEnderManPackPayload(buf.readInt())
        );
        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    /**
     * 双端通用：注册 C2S/S2C payload 类型 + 服务端处理逻辑。
     * 在 common 初始化(onInitialize)调用，客户端与服务端都会执行 payload 类型注册。
     */
    public static void init() {
        PayloadTypeRegistry.playC2S().register(RequestEnderManListPayload.ID, RequestEnderManListPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(OpenEnderManPackPayload.ID, OpenEnderManPackPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(EnderManListResponsePayload.ID, EnderManListResponsePayload.CODEC);

        // 收到请求列表：收集该玩家绑定的所有已驯服末影人并返回
        ServerPlayNetworking.registerGlobalReceiver(RequestEnderManListPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = (ServerPlayerEntity) context.player();
                List<TameableEnderManEntity> list = collectOwnedEndermen(player);
                int[] ids = new int[list.size()];
                String[] names = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    TameableEnderManEntity e = list.get(i);
                    ids[i] = e.getId();
                    names[i] = e.getDisplayName().getString();
                }
                ServerPlayNetworking.send(player, new EnderManListResponsePayload(ids, names));
            });
        });

        // 收到打开请求：校验归属后远程打开末影人背包
        ServerPlayNetworking.registerGlobalReceiver(OpenEnderManPackPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = (ServerPlayerEntity) context.player();
                TameableEnderManEntity enderman = findEnderman((ServerWorld) player.getEntityWorld(), payload.entityId());
                if (enderman == null || !enderman.isTamed()) return;
                UUID owner = enderman.getOwnerUuid();
                if (owner == null || !owner.equals(player.getUuid())) return;

                Text title = enderman.getDisplayName();
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                        (syncId, inv, p) -> new EnderManScreenHandler(syncId, inv, enderman),
                        title
                ));
            });
        });
    }

    /** 收集某玩家绑定的所有已驯服、已加载的末影人 */
    private static List<TameableEnderManEntity> collectOwnedEndermen(ServerPlayerEntity player) {
        List<TameableEnderManEntity> result = new ArrayList<>();
        for (ServerWorld world : ((ServerWorld) player.getEntityWorld()).getServer().getWorlds()) {
            List<TameableEnderManEntity> inWorld = world.getEntitiesByClass(
                    TameableEnderManEntity.class,
                    new net.minecraft.util.math.Box(-3.0E7, -3.0E7, -3.0E7, 3.0E7, 3.0E7, 3.0E7),
                    entity -> entity.isTamed() && player.getUuid().equals(entity.getOwnerUuid())
            );
            result.addAll(inWorld);
        }
        return result;
    }

    /** 在所有维度中按实体 id 查找末影人 */
    private static TameableEnderManEntity findEnderman(ServerWorld anyWorld, int entityId) {
        for (ServerWorld world : anyWorld.getServer().getWorlds()) {
            if (world.getEntityById(entityId) instanceof TameableEnderManEntity e) {
                return e;
            }
        }
        return null;
    }

    private EnderManNetworking() {
    }
}