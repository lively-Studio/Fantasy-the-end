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
package com.fantasy.end.registry;

import com.fantasy.end.FantasyTheEnd;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModBlocks {

    public static final RegistryKey<Block> ENDER_STONE_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "ender_stone"));

    public static final Block ENDER_STONE = Registry.register(
            Registries.BLOCK,
            ENDER_STONE_KEY,
            new Block(Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_STONE_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_STONE_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_stone"));

    public static final Block PHANTOM_STONE = Registry.register(
            Registries.BLOCK,
            PHANTOM_STONE_KEY,
            new Block(Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_STONE_KEY))
    );

    private ModBlocks() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册方块完成。");
    }
}
