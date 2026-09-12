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

import com.fantasy.end.entity.TameableEnderManEntity;
import com.fantasy.end.screen.EnderManScreenHandler;
import com.fantasy.mob.MobBackpack;
import com.fantasy.mob.MobEntry;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 把末影人的「已驯服列表 / 打开背包」注册到 fantasy_mob 库模组的通用入口。
 * The End 只负责提供末影人特有的收集与开包逻辑；网络与按钮界面由库模组负责。
 */
public final class EnderManMobProvider {

    /** 在服务端初始化时调用，注册 provider 与 opener。 */
    public static void init() {
        // 提供「该玩家已驯服的末影人列表」
        MobBackpack.setListProvider(EnderManMobProvider::collectOwned);
        // 打开指定末影人的背包
        MobBackpack.setOpener(EnderManMobProvider::openBackpack);
    }

    private static List<MobEntry> collectOwned(ServerPlayerEntity player) {
        List<TameableEnderManEntity> list = collectOwnedEndermen(player);
        List<MobEntry> entries = new ArrayList<>(list.size());
        for (TameableEnderManEntity e : list) {
            entries.add(new MobEntry(e.getId(), e.getDisplayName().getString()));
        }
        return entries;
    }

    private static void openBackpack(ServerPlayerEntity player, int entityId) {
        TameableEnderManEntity enderman = findEnderman((ServerWorld) player.getEntityWorld(), entityId);
        if (enderman == null || !enderman.isTamed()) return;
        UUID owner = enderman.getOwnerUuid();
        if (owner == null || !owner.equals(player.getUuid())) return;

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, inv, p) -> new EnderManScreenHandler(syncId, inv, enderman),
                enderman.getDisplayName()
        ));
    }

    /** 收集某玩家绑定的所有已驯服、已加载的末影人 */
    private static List<TameableEnderManEntity> collectOwnedEndermen(ServerPlayerEntity player) {
        List<TameableEnderManEntity> result = new ArrayList<>();
        for (ServerWorld world : ((ServerWorld) player.getEntityWorld()).getServer().getWorlds()) {
            List<TameableEnderManEntity> inWorld = world.getEntitiesByClass(
                    TameableEnderManEntity.class,
                    new Box(-3.0E7, -3.0E7, -3.0E7, 3.0E7, 3.0E7, 3.0E7),
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

    private EnderManMobProvider() {
    }
}