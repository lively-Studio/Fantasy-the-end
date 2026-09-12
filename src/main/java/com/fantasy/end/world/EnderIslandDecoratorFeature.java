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
package com.fantasy.end.world;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

/**
 * Phase 1.3b：末地主岛地形点缀（参考 BetterEnd 起伏地表思路的轻量实现）。
 * 在末地生物群系（含主岛与外围浮岛）表面随机添加隆起的小丘，
 * 部分小丘顶带紫珀柱 / 哭泣黑曜石 / 末影水晶点缀，制造起伏与"晶体"观感。
 *
 * 仅使用原生 1.21.11 API（StructureWorldAccess.setBlockState），保证云端 CI 可编译。
 */
public class EnderIslandDecoratorFeature extends Feature<DefaultFeatureConfig> {

    public EnderIslandDecoratorFeature(com.mojang.serialization.Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        // 稀疏：约 1/3 chunk 生成一个小丘，避免过度密集
        if (random.nextFloat() > 0.33F) {
            return false;
        }

        int bx = origin.getX() + random.nextInt(16);
        int bz = origin.getZ() + random.nextInt(16);

        // 避开末地 0,0 中央平台 / 龙塔区，保持龙战场地整洁
        if (Math.abs(bx) < 16 && Math.abs(bz) < 16) {
            return false;
        }

        // 找地表高度（浮岛表面）
        int baseY = world.getTopY(Heightmap.Type.WORLD_SURFACE, bx, bz);
        if (baseY <= world.getBottomY() + 4) {
            return false;
        }

        int hillHeight = 2 + random.nextInt(3);   // 2..4
        int radius = 2 + random.nextInt(2);       // 2..3（小丘基座）
        BlockPos.Mutable pos = new BlockPos.Mutable();

        // 自地表往上垒出逐层收窄的圆顶小丘（只添空位，避免破坏浮岛）
        for (int layer = 0; layer <= hillHeight; layer++) {
            int rr = Math.max(1, radius - layer / 2);
            int r2 = rr * rr;
            for (int dx = -rr; dx <= rr; dx++) {
                for (int dz = -rr; dz <= rr; dz++) {
                    if (dx * dx + dz * dz > r2) {
                        continue;
                    }
                    pos.set(bx + dx, baseY + layer, bz + dz);
                    if (world.getBlockState(pos).isReplaceable()) {
                        world.setBlockState(pos, Blocks.END_STONE.getDefaultState(), 3);
                    }
                }
            }
        }

        // 顶部点缀：紫珀柱 + （低频）哭泣黑曜石 / 末影水晶
        int topY = baseY + hillHeight;
        int pillar = random.nextInt(3);
        for (int i = 1; i <= pillar; i++) {
            pos.set(bx, topY + i, bz);
            if (world.getBlockState(pos).isReplaceable()) {
                world.setBlockState(pos, Blocks.PURPUR_BLOCK.getDefaultState(), 3);
            }
        }

        float tip = random.nextFloat();
        pos.set(bx, topY + pillar + 1, bz);
        if (tip < 0.25F) {
            if (world.getBlockState(pos).isReplaceable()) {
                world.setBlockState(pos, Blocks.CRYING_OBSIDIAN.getDefaultState(), 3);
            }
        }

        // 少量水晶点缀（晶体观感），绝不触碰守卫龙水晶
        if (tip >= 0.60F && tip < 0.78F && world instanceof ServerWorld serverWorld) {
            EndCrystalEntity crystal = EntityType.END_CRYSTAL.create(serverWorld, SpawnReason.STRUCTURE);
            if (crystal != null) {
                crystal.setInvulnerable(false);
                crystal.refreshPositionAndAngles(
                        bx + 0.5D, topY + pillar + 1.5D, bz + 0.5D, random.nextFloat() * 360.0F, 0.0F);
                serverWorld.spawnEntity(crystal);
            }
        }

        return true;
    }
}