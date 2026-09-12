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
 * 末地主岛黑曜石柱改版（参考 BetterEnd 的 SpikeFeatureMixin 思路）。
 * 注入原版 EndSpikeFeature.generateSpike：取消原版"等直径圆柱黑曜石柱"，
 * 改为生成自末地表面升起的、带哭泣黑曜石点缀的收拢锥形高塔，
 * 顶部保留守卫水晶笼与末影水晶。
 *
 * 依赖说明：本类仅使用 Minecraft 原生 API（ServerWorld.setBlockState 等），
 * 替代 BetterEnd 中 BCLib 的 BlocksHelper/StructureHelper，并对 1.21.11 Yarn API 适配。
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

        // 塔从末地主岛地表升起
        int baseY = serverWorld.getTopY(Heightmap.Type.WORLD_SURFACE, cx, cz);
        if (baseY < bottomY) {
            baseY = bottomY;
        }
        int topY = baseY + height;

        // 1) 建收拢锥形塔（含向下的锚固段）
        int anchor = 8;
        for (int y = baseY - anchor; y <= topY; y++) {
            int distFromBase = y - (baseY - anchor);
            int r = Math.max(1, radius - distFromBase / 4);
            int r2 = r * r + 1;
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (dx * dx + dz * dz <= r2) {
                        cursor.set(cx + dx, y, cz + dz);
                        if (serverWorld.getBlockState(cursor).isReplaceable()) {
                            boolean edge = dx == r || dx == -r || dz == r || dz == -r;
                            serverWorld.setBlockState(cursor, edge && random.nextFloat() < CRYING_CHANCE
                                    ? Blocks.CRYING_OBSIDIAN.getDefaultState()
                                    : Blocks.OBSIDIAN.getDefaultState());
                        }
                    }
                }
            }
        }

        // 2) 顶部承载平台（基岩）与末影水晶
        cursor.set(cx, topY, cz);
        serverWorld.setBlockState(cursor, Blocks.BEDROCK.getDefaultState());

        EndCrystalEntity crystal = EntityType.END_CRYSTAL.create(serverWorld, SpawnReason.STRUCTURE);
        if (crystal != null) {
            crystal.setBeamTarget(config.getPos());
            crystal.setInvulnerable(config.isCrystalInvulnerable());
            crystal.refreshPositionAndAngles(
                    cx + 0.5D, topY + 1.0D, cz + 0.5D, random.nextFloat() * 360.0F, 0.0F);
            serverWorld.spawnEntity(crystal);
            BlockPos cpos = crystal.getBlockPos();
            serverWorld.setBlockState(cpos.down(), Blocks.BEDROCK.getDefaultState());
            serverWorld.setBlockState(cpos, Blocks.FIRE.getDefaultState());
        }

        // 3) 守卫水晶的铁栅栏笼（仅 guarded 尖塔）
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
                            serverWorld.setBlockState(cursor, state);
                        }
                    }
                }
            }
        }

        ci.cancel();
    }
}