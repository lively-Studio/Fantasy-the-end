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

package com.fantasy.end.handler;

import com.fantasy.end.registry.ModStatusEffects;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CartographyTableBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.LoomBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.TrapdoorBlock;
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
        BlockState state = world.getBlockState(hit.getBlockPos());
        if (isInteractableBlock(state)) return ActionResult.PASS;

        Direction side = hit.getSide();
        Vec3d target = Vec3d.ofCenter(hit.getBlockPos().offset(side));
        return doTeleport(player, world, target);
    }

    private static boolean isInteractableBlock(BlockState state) {
        Object b = state.getBlock();
        return b instanceof DoorBlock
                || b instanceof TrapdoorBlock
                || b instanceof FenceGateBlock
                || b instanceof LeverBlock
                || b instanceof BlockWithEntity
                || b instanceof ChestBlock
                || b instanceof EnderChestBlock
                || b instanceof BarrelBlock
                || b instanceof ShulkerBoxBlock
                || b instanceof CraftingTableBlock
                || b instanceof EnchantingTableBlock
                || b instanceof AnvilBlock
                || b instanceof LoomBlock
                || b instanceof CartographyTableBlock
                || b instanceof GrindstoneBlock
                || b instanceof StonecutterBlock
                || b instanceof BedBlock
                || b instanceof NoteBlock
                || b instanceof JukeboxBlock
                || b instanceof CakeBlock
                || b instanceof RespawnAnchorBlock;
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
