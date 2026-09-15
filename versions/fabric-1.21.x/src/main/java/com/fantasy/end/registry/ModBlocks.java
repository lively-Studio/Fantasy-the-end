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

package com.fantasy.end.registry;

import com.fantasy.end.FantasyTheEnd;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public final class ModBlocks {

    public static final RegistryKey<Block> ENDER_ORE_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "ender_ore"));

    public static final Block ENDER_ORE = Registry.register(
            Registries.BLOCK,
            ENDER_ORE_KEY,
            new Block(Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_ORE_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_ORE_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_ore"));

    public static final Block PHANTOM_ORE = Registry.register(
            Registries.BLOCK,
            PHANTOM_ORE_KEY,
            new Block(Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_ORE_KEY))
    );

    public static final RegistryKey<Block> ENDER_SLAB_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "ender_slab"));

    public static final SlabBlock ENDER_SLAB = Registry.register(
            Registries.BLOCK,
            ENDER_SLAB_KEY,
            new SlabBlock(Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_SLAB_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_SLAB_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_slab"));

    public static final SlabBlock PHANTOM_SLAB = Registry.register(
            Registries.BLOCK,
            PHANTOM_SLAB_KEY,
            new SlabBlock(Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_SLAB_KEY))
    );

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

    public static final RegistryKey<Block> ENDER_STONE_SLAB_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "ender_stone_slab"));

    public static final SlabBlock ENDER_STONE_SLAB = Registry.register(
            Registries.BLOCK,
            ENDER_STONE_SLAB_KEY,
            new SlabBlock(Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_STONE_SLAB_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_STONE_SLAB_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_stone_slab"));

    public static final SlabBlock PHANTOM_STONE_SLAB = Registry.register(
            Registries.BLOCK,
            PHANTOM_STONE_SLAB_KEY,
            new SlabBlock(Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_STONE_SLAB_KEY))
    );

    public static final RegistryKey<Block> ENDER_DOOR_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "ender_door"));

    public static final DoorBlock ENDER_DOOR = Registry.register(
            Registries.BLOCK,
            ENDER_DOOR_KEY,
            new DoorBlock(BlockSetType.STONE,
                    Block.Settings.copy(Blocks.END_STONE)
                    .nonOpaque()
                    .strength(3.0F)
                    .sounds(BlockSoundGroup.STONE)
                    .registryKey(ENDER_DOOR_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_DOOR_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_door"));

    public static final DoorBlock PHANTOM_DOOR = Registry.register(
            Registries.BLOCK,
            PHANTOM_DOOR_KEY,
            new DoorBlock(BlockSetType.STONE,
                    Block.Settings.copy(Blocks.END_STONE)
                    .nonOpaque()
                    .strength(3.0F)
                    .sounds(BlockSoundGroup.STONE)
                    .registryKey(PHANTOM_DOOR_KEY))
    );

    public static final RegistryKey<Block> ENDER_TRAPDOOR_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "ender_trapdoor"));

    public static final TrapdoorBlock ENDER_TRAPDOOR = Registry.register(
            Registries.BLOCK,
            ENDER_TRAPDOOR_KEY,
            new TrapdoorBlock(BlockSetType.STONE,
                    Block.Settings.copy(Blocks.END_STONE)
                    .nonOpaque()
                    .strength(3.0F)
                    .sounds(BlockSoundGroup.STONE)
                    .registryKey(ENDER_TRAPDOOR_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_TRAPDOOR_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_trapdoor"));

    public static final TrapdoorBlock PHANTOM_TRAPDOOR = Registry.register(
            Registries.BLOCK,
            PHANTOM_TRAPDOOR_KEY,
            new TrapdoorBlock(BlockSetType.STONE,
                    Block.Settings.copy(Blocks.END_STONE)
                    .nonOpaque()
                    .strength(3.0F)
                    .sounds(BlockSoundGroup.STONE)
                    .registryKey(PHANTOM_TRAPDOOR_KEY))
    );

    // ===== 末地事件/陨石方块 =====

    public static final RegistryKey<Block> END_METEORITE_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "end_meteorite"));

    public static final Block END_METEORITE = Registry.register(
            Registries.BLOCK,
            END_METEORITE_KEY,
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 7),
                    Block.Settings.copy(Blocks.END_STONE)
                    .strength(4.0F)
                    .resistance(6.0F)
                    .sounds(BlockSoundGroup.STONE)
                    .registryKey(END_METEORITE_KEY))
    );

    private ModBlocks() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册方块完成。");
    }
}
