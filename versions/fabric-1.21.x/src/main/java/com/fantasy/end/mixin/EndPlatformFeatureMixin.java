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

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.feature.EndPlatformFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Phase 1.3a：末地主岛中央祭坛（参考 BetterEnd 中央建筑群思路）。
 * 注入原版 EndPlatformFeature.generate（负责 0,0 平台生成）：在平台四角上方
 * 悬空生成 4 座 BetterEnd 风格的末地祭坛小尖塔（末地石基座 + 紫珀高柱 + 顶部末影水晶），
 * 与 0,0 既有的锥形黑曜石主塔错开、互不干扰。
 *
 * 一致性说明：
 * - 仅当传入 pos 位于末地中央（|x|,|z| <= 2）时生成，避免在次要平台误建。
 * - 祭坛抬升到 pos.y()+4（高于 EndPlatformFeature 清空的 +0..+3），且位于 5x5 核心之外，
 *   因此末影龙重刷平台（respawn 幂等 re-generate）时不会被覆盖。
 */
@Mixin(EndPlatformFeature.class)
public abstract class EndPlatformFeatureMixin {

    /** 祭坛与 0,0 中心的水平距离（错开 5x5 平台核心区） */
    private static final int SHRINE_DIST = 5;
    /** 祭坛比平台中心的抬升高度（高于平台清空区，respawn 幂等） */
    private static final int SHRINE_LIFT = 4;

    @Inject(method = "generate(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Z)V",
            at = @At("RETURN"))
    private static void fantasy_end_generateCentralShrines(
            ServerWorldAccess world, BlockPos pos, boolean determinedLocation, CallbackInfo ci) {
        // 守卫：仅处理末地 0,0 中央平台
        if (Math.abs(pos.getX()) > 2 || Math.abs(pos.getZ()) > 2) {
            return;
        }
        int[][] offsets = { {1, 1}, {-1, 1}, {1, -1}, {-1, -1} };
        for (int[] off : offsets) {
            BlockPos base = pos.add(off[0] * SHRINE_DIST, SHRINE_LIFT, off[1] * SHRINE_DIST);
            buildShrine(world, base);
        }
    }

    private static void buildShrine(ServerWorldAccess world, BlockPos base) {
        BlockPos.Mutable p = new BlockPos.Mutable();

        // 底座 3x3 末地石（悬空小岛）
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                p.set(base.getX() + x, base.getY(), base.getZ() + z);
                world.setBlockState(p, Blocks.END_STONE.getDefaultState(), 3);
            }
        }

        // 中央紫珀高柱（4 格）
        for (int y = 1; y <= 4; y++) {
            p.set(base.getX(), base.getY() + y, base.getZ());
            world.setBlockState(p, Blocks.PURPUR_BLOCK.getDefaultState(), 3);
        }

        // 四角末地石墩
        int[][] corners = { {-1, -1}, {1, -1}, {-1, 1}, {1, 1} };
        for (int[] c : corners) {
            p.set(base.getX() + c[0], base.getY() + 1, base.getZ() + c[1]);
            world.setBlockState(p, Blocks.END_STONE_BRICKS.getDefaultState(), 3);
        }

        // 顶部末影水晶柱盖
        p.set(base.getX(), base.getY() + 5, base.getZ());
        world.setBlockState(p, Blocks.PURPUR_PILLAR.getDefaultState(), 3);

        if (world instanceof ServerWorld serverWorld) {
            EndCrystalEntity crystal = EntityType.END_CRYSTAL.create(serverWorld, SpawnReason.STRUCTURE);
            if (crystal != null) {
                crystal.setInvulnerable(false);
                crystal.refreshPositionAndAngles(
                        base.getX() + 0.5D, base.getY() + 6.0D, base.getZ() + 0.5D, 0.0F, 0.0F);
                serverWorld.spawnEntity(crystal);
            }
        }
    }
}