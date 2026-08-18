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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public abstract class EntityTypeFactoryMixin {

    @SuppressWarnings("unchecked")
    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private void onCreate(World world, SpawnReason reason, CallbackInfoReturnable<Entity> cir) {
        EntityType<?> self = (EntityType<?>) (Object) this;
        // 替换所有原版末影人生成为可驯服末影人
        if (self == EntityType.ENDERMAN) {
            Entity entity = new TameableEnderManEntity(
                    (EntityType<? extends EndermanEntity>) (EntityType<?>) EntityType.ENDERMAN,
                    world
            );
            cir.setReturnValue(entity);
        }
    }
}