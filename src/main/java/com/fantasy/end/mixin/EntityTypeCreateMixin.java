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
package com.fantasy.end.mixin;

import com.fantasy.end.entity.TameableEnderManEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 拦截 EntityType 中的实体创建方法。
 * 在 1.21.11 中，create(World, SpawnReason) 是所有实体创建的底层入口：
 * 6 参数版 create(ServerWorld, Consumer, BlockPos, SpawnReason, boolean, boolean) 与
 * NBT 加载 (getEntityFromData) 最终都调用它。
 * 在此将原版末影人替换为可驯服末影人(TameableEnderManEntity)。
 *
 * 覆盖所有创建路径：
 * - 自然生成 (MobSpawnerLogic → 6参数 create → 2参数 create)
 * - 刷怪蛋 (SpawnEggItem → create)
 * - 命令 (/summon → EntityType.summon → create)
 * - NBT加载 (getEntityFromData → create)
 */
@Mixin(EntityType.class)
public abstract class EntityTypeCreateMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("FantasyTheEnd-EntityType");

    /**
     * 拦截 EntityType.create(World, SpawnReason) —— 1.21.11 中的底层创建入口。
     * 注意第一个参数是 World（基类），不是 ServerWorld。
     */
    @SuppressWarnings("unchecked")
    @Inject(method = "create(Lnet/minecraft/world/World;Lnet/minecraft/entity/SpawnReason;)Lnet/minecraft/entity/Entity;", at = @At("HEAD"), cancellable = true)
    private void fantasyTheEnd$onCreate(World world, SpawnReason reason, CallbackInfoReturnable<Entity> cir) {
        EntityType<?> type = (EntityType<?>) (Object) this;
        if (type == EntityType.ENDERMAN) {
            try {
                Entity entity = new TameableEnderManEntity(
                        (EntityType<? extends EndermanEntity>) (EntityType<?>) EntityType.ENDERMAN,
                        world
                );
                cir.setReturnValue(entity);
                LOGGER.debug("[幻想:末地] 末影人已替换为 TameableEnderManEntity (原因: {})", reason);
            } catch (Exception e) {
                LOGGER.error("[幻想:末地] 替换末影人实体失败，保留原版。原因: {}", e.getMessage(), e);
            }
        }
    }
}
