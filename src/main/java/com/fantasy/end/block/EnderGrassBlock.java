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

import com.fantasy.end.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class EnderGrassBlock extends PlantBlock {

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
}
