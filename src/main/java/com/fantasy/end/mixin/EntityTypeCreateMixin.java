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
import com.fantasy.end.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 拦截 EntityType.create(World, SpawnReason)，
 * 把原版末影人替换为可驯服的 TameableEnderManEntity。
 *
 * 关键：必须用 ModEntities.TAMEABLE_ENDER_MAN 作为 EntityType，
 * 不能用 EntityType.ENDERMAN。后者在 1.21.11 中缺少 TameableEnderManEntity
 * 构造时依赖的字段，会导致 Entity 父类初始化 NPE。
 */
@Mixin(EntityType.class)
public abstract class EntityTypeCreateMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("FantasyTheEnd-EntityType");

    @Inject(
            method = "create(Lnet/minecraft/world/World;Lnet/minecraft/entity/SpawnReason;)Lnet/minecraft/entity/Entity;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void fantasyTheEnd$onCreate(World world, SpawnReason reason, CallbackInfoReturnable<Entity> cir) {
        EntityType<?> type = (EntityType<?>) (Object) this;
        if (type == EntityType.ENDERMAN) {
            try {
                // 走标准 create 流程，用正确的 EntityType
                Entity entity = ModEntities.TAMEABLE_ENDER_MAN.create(world, reason);
                if (entity != null) {
                    cir.setReturnValue(entity);
                    LOGGER.debug("[幻想:末地] 末影人已替换为 TameableEnderManEntity (原因: {})", reason);
                }
            } catch (Exception e) {
                LOGGER.error("[幻想:末地] 替换末影人实体失败，保留原版。原因: {}", e.getMessage(), e);
            }
        }
    }
}
