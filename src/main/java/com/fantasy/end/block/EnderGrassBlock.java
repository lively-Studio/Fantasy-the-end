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
package com.fantasy.end.block;

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class EnderGrassBlock extends PlantBlock implements Fertilizable {

    public static final MapCodec<EnderGrassBlock> CODEC = Block.createCodec(EnderGrassBlock::new);

    private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.1, 0.0, 0.1, 0.9, 0.8, 0.9);

    @Override
    public MapCodec<? extends EnderGrassBlock> getCodec() {
        return CODEC;
    }

    public EnderGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.END_STONE)
                || floor.isOf(ModBlocks.ENDER_STONE)
                || floor.isOf(ModBlocks.PHANTOM_STONE)
                || floor.isOf(ModBlocks.ENDER_ORE)
                || floor.isOf(ModBlocks.PHANTOM_ORE);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        // 10% 概率催生新的晶体草
        if (random.nextInt(10) == 0) {
            Block crystalGrass = Registries.BLOCK.get(Identifier.of(FantasyTheEnd.MOD_ID, "crystal_grass"));
            for (int i = 0; i < 8; i++) {
                BlockPos target = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
                if (world.getBlockState(target).isAir()
                        && crystalGrass.getDefaultState().canPlaceAt(world, target)) {
                    world.setBlockState(target, crystalGrass.getDefaultState(), Block.NOTIFY_ALL);
                }
            }
        }
    }
}
