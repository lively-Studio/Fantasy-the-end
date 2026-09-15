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

package com.fantasy.end.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 末地主岛黑曜石柱改版。
 *
 * 关键：世界生成期间所有方块读写、高度图查询必须走 world（ChunkRegion），
 * 不能用 serverWorld。serverWorld.getTopY / getBlockState / setBlockState
 * 都会触发 ServerChunkManager 的同步区块加载，而当前线程正是负责生成该区块的线程，
 * 造成自等待死锁，进而拖死整个服务端。
 */
@Mixin(EndSpikeFeature.class)
public abstract class EndSpikeFeatureMixin {

    private static final float CRYING_CHANCE = 1.0F / 24.0F;

    @Inject(method = "generateSpike", at = @At("HEAD"), cancellable = true)
    private void fantasy_end_reworkSpike(
            ServerWorldAccess world,
            Random random,
            EndSpikeFeatureConfig config,
            EndSpikeFeature.Spike spike,
            CallbackInfo ci
    ) {
        int cx = spike.getCenterX();
        int cz = spike.getCenterZ();
        int radius = spike.getRadius();
        int height = spike.getHeight();

        ServerWorld serverWorld = world.toServerWorld();
        BlockPos.Mutable cursor = new BlockPos.Mutable();
        int bottomY = world.getBottomY();

        // 用 world.getTopY（ChunkRegion 实现，只读当前生成中的区块，不触发异步加载）
        int baseY = world.getTopY(Heightmap.Type.WORLD_SURFACE, cx, cz);
        if (baseY < bottomY) {
            baseY = bottomY;
        }
        int topY = baseY + height;

        // 1) 收拢锥形塔
        int anchor = 8;
        for (int y = baseY - anchor; y <= topY; y++) {
            int distFromBase = y - (baseY - anchor);
            int r = Math.max(1, radius - distFromBase / 4);
            int r2 = r * r + 1;
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (dx * dx + dz * dz <= r2) {
                        cursor.set(cx + dx, y, cz + dz);
                        if (world.getBlockState(cursor).isReplaceable()) {
                            boolean edge = dx == r || dx == -r || dz == r || dz == -r;
                            world.setBlockState(cursor,
                                    edge && random.nextFloat() < CRYING_CHANCE
                                            ? Blocks.CRYING_OBSIDIAN.getDefaultState()
                                            : Blocks.OBSIDIAN.getDefaultState(),
                                    Block.NOTIFY_ALL);
                        }
                    }
                }
            }
        }

        // 2) 顶部基岩平台与末影水晶
        cursor.set(cx, topY, cz);
        world.setBlockState(cursor, Blocks.BEDROCK.getDefaultState(), Block.NOTIFY_ALL);

        EndCrystalEntity crystal = EntityType.END_CRYSTAL.create(serverWorld, SpawnReason.STRUCTURE);
        if (crystal != null) {
            crystal.setBeamTarget(config.getPos());
            crystal.setInvulnerable(config.isCrystalInvulnerable());
            crystal.refreshPositionAndAngles(
                    cx + 0.5D, topY + 1.0D, cz + 0.5D, random.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(crystal);
            BlockPos cpos = crystal.getBlockPos();
            world.setBlockState(cpos.down(), Blocks.BEDROCK.getDefaultState(), Block.NOTIFY_ALL);
            world.setBlockState(cpos, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        }

        // 3) 铁栏杆笼
        if (spike.isGuarded()) {
            for (int px = -2; px <= 2; px++) {
                boolean pxOuter = MathHelper.abs(px) == 2;
                for (int pz = -2; pz <= 2; pz++) {
                    boolean pzOuter = MathHelper.abs(pz) == 2;
                    for (int py = 0; py <= 3; py++) {
                        boolean pyTop = py == 3;
                        if (pxOuter || pzOuter || pyTop) {
                            boolean bl4 = px == -2 || px == 2 || pyTop;
                            boolean bl5 = pz == -2 || pz == 2 || pyTop;
                            var state = Blocks.IRON_BARS.getDefaultState()
                                    .with(Properties.NORTH, bl4 && pz != -2)
                                    .with(Properties.SOUTH, bl4 && pz != 2)
                                    .with(Properties.WEST, bl5 && px != -2)
                                    .with(Properties.EAST, bl5 && px != 2);
                            cursor.set(cx + px, topY + py, cz + pz);
                            world.setBlockState(cursor, state, Block.NOTIFY_ALL);
                        }
                    }
                }
            }
        }

        ci.cancel();
    }
}