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
