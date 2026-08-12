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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class EnderFlowerBlock extends FlowerBlock {

    public static final MapCodec<EnderFlowerBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    STEW_EFFECT_CODEC.forGetter(FlowerBlock::getStewEffects),
                    createSettingsCodec()
            ).apply(instance, EnderFlowerBlock::new)
    );

    @Override
    public MapCodec<EnderFlowerBlock> getCodec() {
        return CODEC;
    }

    public EnderFlowerBlock(RegistryEntry<StatusEffect> stewEffect, float duration, Settings settings) {
        super(stewEffect, duration, settings);
    }

    public EnderFlowerBlock(SuspiciousStewEffectsComponent effects, Settings settings) {
        super(effects, settings);
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
