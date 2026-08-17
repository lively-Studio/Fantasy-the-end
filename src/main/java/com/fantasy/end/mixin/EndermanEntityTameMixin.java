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

import com.fantasy.end.item.PurplePoppedChorusFruitItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityTameMixin {

    @Unique
    private static final TrackedData<Boolean> TAMED =
            DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final TrackedData<String> OWNER_UUID_STRING =
            DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.STRING);

    // ========== 数据追踪初始化 ==========

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void onInitDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(TAMED, false);
        builder.add(OWNER_UUID_STRING, "");
    }

    // ========== 驯服方法 ==========

    @Unique
    private boolean fantasy$isTamed() {
        return ((EndermanEntity)(Object)this).getDataTracker().get(TAMED);
    }

    @Unique
    private void fantasy$setTamed(boolean tamed) {
        EndermanEntity self = (EndermanEntity)(Object)this;
        self.getDataTracker().set(TAMED, tamed);
        if (tamed) {
            self.setDespawnCounter(0);
        }
    }

    @Unique
    private UUID fantasy$getOwnerUuid() {
        EndermanEntity self = (EndermanEntity)(Object)this;
        String uuidStr = self.getDataTracker().get(OWNER_UUID_STRING);
        if (uuidStr.isEmpty()) return null;
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Unique
    private void fantasy$setOwnerUuid(UUID uuid) {
        EndermanEntity self = (EndermanEntity)(Object)this;
        self.getDataTracker().set(OWNER_UUID_STRING, uuid != null ? uuid.toString() : "");
    }

    @Unique
    private PlayerEntity fantasy$getOwner() {
        EndermanEntity self = (EndermanEntity)(Object)this;
        UUID uuid = fantasy$getOwnerUuid();
        if (uuid == null || !(self.getEntityWorld() instanceof ServerWorld serverWorld)) {
            return null;
        }
        return serverWorld.getPlayerByUuid(uuid);
    }

    @Unique
    private boolean fantasy$tame(PlayerEntity player) {
        EndermanEntity self = (EndermanEntity)(Object)this;
        fantasy$setTamed(true);
        fantasy$setOwnerUuid(player.getUuid());
        self.setAngryAt(null);
        self.setTarget(null);
        if (self.getEntityWorld() instanceof ServerWorld) {
            self.getNavigation().stop();
        }
        return true;
    }

    // ========== 交互 - 驯服 ==========

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        EndermanEntity self = (EndermanEntity)(Object)this;

        // 已驯服的跳过（由 TameableEnderManEntity 或其他逻辑处理）
        if (fantasy$isTamed()) return;

        // 检查是否手持紫松果
        if (player.getStackInHand(hand).getItem() instanceof PurplePoppedChorusFruitItem) {
            if (!self.getEntityWorld().isClient()) {
                // 消耗紫松果
                if (!player.isCreative()) {
                    player.getStackInHand(hand).decrement(1);
                }
                fantasy$tame(player);
                self.getEntityWorld().sendEntityStatus(self, (byte) 18); // 爱心粒子
            }
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    // ========== 驯服后行为 ==========

    @Inject(method = "isAngry", at = @At("HEAD"), cancellable = true)
    private void onIsAngry(CallbackInfoReturnable<Boolean> cir) {
        if (fantasy$isTamed()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "teleportRandomly", at = @At("HEAD"), cancellable = true)
    private void onTeleportRandomly(CallbackInfoReturnable<Boolean> cir) {
        if (fantasy$isTamed()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "canImmediatelyDespawn", at = @At("HEAD"), cancellable = true)
    private void onCanImmediatelyDespawn(double distance, CallbackInfoReturnable<Boolean> cir) {
        if (fantasy$isTamed()) {
            cir.setReturnValue(false);
        }
    }

    // ========== NBT 持久化 ==========

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void onWriteCustomData(WriteView nbt, CallbackInfo ci) {
        EndermanEntity self = (EndermanEntity)(Object)this;
        nbt.putBoolean("FantasyTamed", fantasy$isTamed());

        UUID ownerUuid = fantasy$getOwnerUuid();
        if (ownerUuid != null) {
            nbt.putString("FantasyOwner", ownerUuid.toString());
        }
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void onReadCustomData(ReadView nbt, CallbackInfo ci) {
        fantasy$setTamed(nbt.getBoolean("FantasyTamed", false));

        nbt.getOptionalString("FantasyOwner").ifPresent(uuidStr -> {
            try {
                fantasy$setOwnerUuid(UUID.fromString(uuidStr));
            } catch (IllegalArgumentException ignored) {
            }
        });
    }
}