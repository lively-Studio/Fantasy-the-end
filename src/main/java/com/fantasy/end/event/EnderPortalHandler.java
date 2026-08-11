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
package com.fantasy.end.event;

import com.fantasy.end.registry.ModStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class EnderPortalHandler implements ServerTickEvents.EndTick {

    private int tickCounter = 0;

    @Override
    public void onEndTick(MinecraftServer server) {
        tickCounter++;
        if (tickCounter % 40 != 0) return;

        for (ServerWorld world : server.getWorlds()) {
            if (world.isClient()) continue;

            for (ServerPlayerEntity player : world.getPlayers()) {
                StatusEffectInstance effect = player.getStatusEffect(ModStatusEffects.ENDER);
                if (effect == null) continue;

                BlockPos playerPos = player.getBlockPos();

                for (int dx = -3; dx <= 3; dx++) {
                    for (int dz = -3; dz <= 3; dz++) {
                        BlockPos center = playerPos.add(dx, 0, dz);
                        if (isPortalCenter(world, center)) {
                            activatePortal(world, center);
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean isPortalCenter(ServerWorld world, BlockPos center) {
        if (!world.getBlockState(center).isAir()) return false;

        int frameCount = 0;
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.abs(x) == 2 && Math.abs(z) == 2) continue;
                if (Math.abs(x) == 2 || Math.abs(z) == 2) {
                    BlockPos framePos = center.add(x, 0, z);
                    BlockState state = world.getBlockState(framePos);
                    if (state.getBlock() instanceof EndPortalFrameBlock) {
                        frameCount++;
                    }
                }
            }
        }
        return frameCount >= 4;
    }

    private void activatePortal(ServerWorld world, BlockPos center) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.abs(x) == 2 && Math.abs(z) == 2) continue;
                if (Math.abs(x) == 2 || Math.abs(z) == 2) {
                    BlockPos framePos = center.add(x, 0, z);
                    BlockState state = world.getBlockState(framePos);
                    if (state.getBlock() instanceof EndPortalFrameBlock) {
                        if (!state.get(EndPortalFrameBlock.EYE)) {
                            world.setBlockState(framePos, state.with(EndPortalFrameBlock.EYE, true), 3);
                        }
                    }
                }
            }
        }

        BlockState portalState = Blocks.END_PORTAL.getDefaultState();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos portalPos = center.add(x, 0, z);
                if (world.getBlockState(portalPos).isAir()) {
                    world.setBlockState(portalPos, portalState, 3);
                }
            }
        }
    }
}
