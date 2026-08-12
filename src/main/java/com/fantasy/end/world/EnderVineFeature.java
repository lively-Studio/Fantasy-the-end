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

import com.fantasy.end.registry.ModPlants;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Map;

public class EnderVineFeature extends Feature<DefaultFeatureConfig> {

    public EnderVineFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        boolean placed = false;

        for (int i = 0; i < 64; i++) {
            BlockPos pos = origin.add(
                    random.nextInt(8) - random.nextInt(8),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(8) - random.nextInt(8));
            if (!world.getBlockState(pos).isAir()) {
                continue;
            }
            BlockState base = ModPlants.ENDER_VINE.getDefaultState();
            BlockState state = base;
            for (Map.Entry<Direction, BooleanProperty> entry : VineBlock.FACING_PROPERTIES.entrySet()) {
                if (VineBlock.shouldConnectTo(world, pos, entry.getKey())) {
                    state = state.with(entry.getValue(), true);
                }
            }
            if (state != base) {
                world.setBlockState(pos, state, 3);
                placed = true;
            }
        }
        return placed;
    }
}
