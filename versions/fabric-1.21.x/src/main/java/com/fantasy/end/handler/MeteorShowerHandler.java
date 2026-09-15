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

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.registry.ModBlocks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.List;

/**
 * 末地流星雨事件处理器
 *
 * 每隔一段时间在末地外岛玩家附近随机降下陨石方块。
 * 陨石落地后会留下一小块 end_meteorite 方块簇，可挖掘获得经验和原石。
 * 玩家拥有末影效果时能感知到流星雨（粒子提示）。
 */
public class MeteorShowerHandler implements ServerTickEvents.EndTick {

    private static final int EVENT_INTERVAL = 12000; // 约10分钟（tick）
    private static final int EVENT_DURATION = 1200;  // 流星雨持续约1分钟
    private static final int METEOR_INTERVAL = 40;   // 每2秒落一颗

    private int tickCounter = 0;
    private boolean showerActive = false;
    private int showerTicksRemaining = 0;
    private int meteorCooldown = 0;

    @Override
    public void onEndTick(MinecraftServer server) {
        tickCounter++;

        if (!showerActive) {
            if (tickCounter % EVENT_INTERVAL == 0) {
                // 检查是否有玩家在末地
                for (ServerWorld world : server.getWorlds()) {
                    if (world.getRegistryKey().getValue().toString().equals("minecraft:the_end")) {
                        List<ServerPlayerEntity> players = world.getPlayers();
                        if (!players.isEmpty()) {
                            showerActive = true;
                            showerTicksRemaining = EVENT_DURATION;
                            meteorCooldown = 0;
                            FantasyTheEnd.LOGGER.info("[幻想:末地] 末地流星雨事件触发！");
                            break;
                        }
                    }
                }
            }
            return;
        }

        // 流星雨进行中
        showerTicksRemaining--;
        if (showerTicksRemaining <= 0) {
            showerActive = false;
            FantasyTheEnd.LOGGER.info("[幻想:末地] 末地流星雨事件结束。");
            return;
        }

        meteorCooldown--;
        if (meteorCooldown > 0) return;
        meteorCooldown = METEOR_INTERVAL;

        // 在末地世界随机玩家附近落陨石
        for (ServerWorld world : server.getWorlds()) {
            if (!world.getRegistryKey().getValue().toString().equals("minecraft:the_end")) continue;

            List<ServerPlayerEntity> players = world.getPlayers();
            if (players.isEmpty()) continue;

            Random random = world.getRandom();

            // 随机选一名玩家作为中心
            ServerPlayerEntity target = players.get(random.nextInt(players.size()));
            BlockPos playerPos = target.getBlockPos();

            // 在玩家 32~96 格范围内随机选位置
            int angle = random.nextInt(360);
            double rad = Math.toRadians(angle);
            int dist = 32 + random.nextInt(64);
            int dx = (int) (MathHelper.cos((float) rad) * dist);
            int dz = (int) (MathHelper.sin((float) rad) * dist);

            int x = playerPos.getX() + dx;
            int z = playerPos.getZ() + dz;

            // 从高处往下找第一个非空气方块
            int y = world.getTopY(net.minecraft.world.Heightmap.Type.MOTION_BLOCKING, x, z) - 1;
            if (y < 1) continue;

            BlockPos meteorPos = new BlockPos(x, y, z);

            // 检查目标位置是末地石类方块
            if (!world.getBlockState(meteorPos).isOf(Blocks.END_STONE) &&
                !world.getBlockState(meteorPos).isOf(ModBlocks.ENDER_STONE) &&
                !world.getBlockState(meteorPos).isOf(ModBlocks.PHANTOM_STONE)) {
                continue;
            }

            // 在落点放置陨石方块（1个主方块+偶尔周围1-2个）
            world.setBlockState(meteorPos, ModBlocks.END_METEORITE.getDefaultState(), 3);

            // 30%概率在旁边再放1-2颗
            if (random.nextFloat() < 0.3F) {
                for (int i = 0; i < 1 + random.nextInt(2); i++) {
                    BlockPos extraPos = meteorPos.add(
                        random.nextInt(3) - 1,
                        0,
                        random.nextInt(3) - 1
                    );
                    if (world.getBlockState(extraPos).isAir() &&
                        world.getBlockState(extraPos.down()).isSolidBlock(world, extraPos.down())) {
                        world.setBlockState(extraPos, ModBlocks.END_METEORITE.getDefaultState(), 3);
                    }
                }
            }

            // 粒子和音效
            world.spawnParticles(ParticleTypes.EXPLOSION,
                x + 0.5, y + 1.0, z + 0.5,
                5, 0.5, 1.0, 0.5, 0.0);

            world.spawnParticles(ParticleTypes.END_ROD,
                x + 0.5, y + 2.0, z + 0.5,
                15, 1.5, 2.0, 1.5, 0.05);

            world.playSound(null, x, y, z,
                SoundEvents.ENTITY_ENDER_DRAGON_SHOOT,
                SoundCategory.BLOCKS, 0.6F, 0.5F);

            world.playSound(null, x, y, z,
                SoundEvents.BLOCK_STONE_BREAK,
                SoundCategory.BLOCKS, 0.8F, 0.7F);
        }
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(new MeteorShowerHandler());
    }
}
