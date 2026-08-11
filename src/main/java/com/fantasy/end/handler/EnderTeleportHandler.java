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
package com.fantasy.end.handler;

import com.fantasy.end.registry.ModStatusEffects;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class EnderTeleportHandler {

    private static final int COOLDOWN_TICKS = 20;
    private static final double MAX_TELEPORT_DISTANCE = 32.0;

    private EnderTeleportHandler() {
    }

    public static void register() {
        UseItemCallback.EVENT.register(EnderTeleportHandler::onUseItem);
        UseBlockCallback.EVENT.register(EnderTeleportHandler::onUseBlock);
    }

    private static ActionResult onUseItem(PlayerEntity player, World world, Hand hand) {
        if (!isValidTeleportContext(player, world, hand)) return ActionResult.PASS;

        HitResult hit = player.raycast(MAX_TELEPORT_DISTANCE, 0.0f, false);
        if (hit.getType() == HitResult.Type.MISS) {
            return doTeleport(player, world, hit.getPos());
        }
        return ActionResult.PASS;
    }

    private static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hit) {
        if (!isValidTeleportContext(player, world, hand)) return ActionResult.PASS;

        Direction side = hit.getSide();
        Vec3d target = Vec3d.ofCenter(hit.getBlockPos().offset(side));
        return doTeleport(player, world, target);
    }

    private static boolean isValidTeleportContext(PlayerEntity player, World world, Hand hand) {
        if (world.isClient()) return false;
        if (hand != Hand.MAIN_HAND) return false;
        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isEmpty()) return false;
        StatusEffectInstance effect = player.getStatusEffect(ModStatusEffects.ENDER);
        if (effect == null) return false;
        if (player.getItemCooldownManager().isCoolingDown(stack)) return false;
        return true;
    }

    private static ActionResult doTeleport(PlayerEntity player, World world, Vec3d target) {
        if (!(world instanceof ServerWorld serverWorld)) return ActionResult.PASS;

        Vec3d safeTarget = findSafeTarget(serverWorld, player, target);

        double fromX = player.getX();
        double fromY = player.getY() + player.getHeight() / 2.0;
        double fromZ = player.getZ();

        player.teleport(safeTarget.getX(), safeTarget.getY(), safeTarget.getZ(), true);

        serverWorld.spawnParticles(
                ParticleTypes.PORTAL,
                fromX, fromY, fromZ,
                16, 0.2, 0.2, 0.2, 0.1
        );
        serverWorld.spawnParticles(
                ParticleTypes.PORTAL,
                safeTarget.getX(), safeTarget.getY() + player.getHeight() / 2.0, safeTarget.getZ(),
                16, 0.2, 0.2, 0.2, 0.1
        );
        serverWorld.playSound(
                null,
                player.getBlockPos(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                SoundCategory.PLAYERS,
                1.0F, 1.0F
        );

        player.getItemCooldownManager().set(player.getMainHandStack(), COOLDOWN_TICKS);

        return ActionResult.SUCCESS;
    }

    private static Vec3d findSafeTarget(ServerWorld world, PlayerEntity player, Vec3d target) {
        double px = target.getX();
        double py = target.getY();
        double pz = target.getZ();
        double halfWidth = player.getWidth() * 0.5;
        double height = player.getHeight();

        for (int dy = 0; dy < 3; dy++) {
            double y = py + dy;
            Box box = new Box(px - halfWidth, y, pz - halfWidth, px + halfWidth, y + height, pz + halfWidth);
            if (canSpawnAt(world, box)) {
                return new Vec3d(px, y, pz);
            }
        }
        for (int dy = -1; dy >= -3; dy--) {
            double y = py + dy;
            Box box = new Box(px - halfWidth, y, pz - halfWidth, px + halfWidth, y + height, pz + halfWidth);
            if (canSpawnAt(world, box)) {
                return new Vec3d(px, y, pz);
            }
        }
        return target;
    }

    private static boolean canSpawnAt(ServerWorld world, Box box) {
        if (!world.isSpaceEmpty(null, box)) return false;
        BlockPos below = BlockPos.ofFloored(box.minX, box.minY - 0.1, box.minZ);
        BlockState state = world.getBlockState(below);
        boolean grounded = !state.isAir() && !state.getFluidState().isIn(FluidTags.WATER);
        return grounded || world.getBlockState(BlockPos.ofFloored(box.getCenter())).isAir();
    }
}
