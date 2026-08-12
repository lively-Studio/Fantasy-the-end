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
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModDecorativeBlocks {

    // ===== 末影之石系列 / Ender Stone series =====

    public static final RegistryKey<Block> ENDER_STONE_STAIRS_KEY = key("ender_stone_stairs");
    public static final StairsBlock ENDER_STONE_STAIRS = Registry.register(
            Registries.BLOCK,
            ENDER_STONE_STAIRS_KEY,
            new StairsBlock(ModBlocks.ENDER_STONE.getDefaultState(),
                    Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_STONE_STAIRS_KEY))
    );
    public static final RegistryKey<Item> ENDER_STONE_STAIRS_ITEM_KEY = itemKey("ender_stone_stairs");
    public static final BlockItem ENDER_STONE_STAIRS_ITEM = Registry.register(
            Registries.ITEM,
            ENDER_STONE_STAIRS_ITEM_KEY,
            new BlockItem(ENDER_STONE_STAIRS, new Item.Settings().registryKey(ENDER_STONE_STAIRS_ITEM_KEY))
    );

    public static final RegistryKey<Block> ENDER_STONE_WALL_KEY = key("ender_stone_wall");
    public static final WallBlock ENDER_STONE_WALL = Registry.register(
            Registries.BLOCK,
            ENDER_STONE_WALL_KEY,
            new WallBlock(Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_STONE_WALL_KEY))
    );
    public static final RegistryKey<Item> ENDER_STONE_WALL_ITEM_KEY = itemKey("ender_stone_wall");
    public static final BlockItem ENDER_STONE_WALL_ITEM = Registry.register(
            Registries.ITEM,
            ENDER_STONE_WALL_ITEM_KEY,
            new BlockItem(ENDER_STONE_WALL, new Item.Settings().registryKey(ENDER_STONE_WALL_ITEM_KEY))
    );

    public static final RegistryKey<Block> ENDER_STONE_FENCE_KEY = key("ender_stone_fence");
    public static final FenceBlock ENDER_STONE_FENCE = Registry.register(
            Registries.BLOCK,
            ENDER_STONE_FENCE_KEY,
            new FenceBlock(Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_STONE_FENCE_KEY))
    );
    public static final RegistryKey<Item> ENDER_STONE_FENCE_ITEM_KEY = itemKey("ender_stone_fence");
    public static final BlockItem ENDER_STONE_FENCE_ITEM = Registry.register(
            Registries.ITEM,
            ENDER_STONE_FENCE_ITEM_KEY,
            new BlockItem(ENDER_STONE_FENCE, new Item.Settings().registryKey(ENDER_STONE_FENCE_ITEM_KEY))
    );

    public static final RegistryKey<Block> ENDER_STONE_FENCE_GATE_KEY = key("ender_stone_fence_gate");
    public static final FenceGateBlock ENDER_STONE_FENCE_GATE = Registry.register(
            Registries.BLOCK,
            ENDER_STONE_FENCE_GATE_KEY,
            new FenceGateBlock(WoodType.OAK,
                    Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_STONE_FENCE_GATE_KEY))
    );
    public static final RegistryKey<Item> ENDER_STONE_FENCE_GATE_ITEM_KEY = itemKey("ender_stone_fence_gate");
    public static final BlockItem ENDER_STONE_FENCE_GATE_ITEM = Registry.register(
            Registries.ITEM,
            ENDER_STONE_FENCE_GATE_ITEM_KEY,
            new BlockItem(ENDER_STONE_FENCE_GATE, new Item.Settings().registryKey(ENDER_STONE_FENCE_GATE_ITEM_KEY))
    );

    public static final RegistryKey<Block> ENDER_STONE_BUTTON_KEY = key("ender_stone_button");
    public static final ButtonBlock ENDER_STONE_BUTTON = Registry.register(
            Registries.BLOCK,
            ENDER_STONE_BUTTON_KEY,
            new ButtonBlock(BlockSetType.STONE, 20,
                    Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_STONE_BUTTON_KEY))
    );
    public static final RegistryKey<Item> ENDER_STONE_BUTTON_ITEM_KEY = itemKey("ender_stone_button");
    public static final BlockItem ENDER_STONE_BUTTON_ITEM = Registry.register(
            Registries.ITEM,
            ENDER_STONE_BUTTON_ITEM_KEY,
            new BlockItem(ENDER_STONE_BUTTON, new Item.Settings().registryKey(ENDER_STONE_BUTTON_ITEM_KEY))
    );

    public static final RegistryKey<Block> ENDER_STONE_PRESSURE_PLATE_KEY = key("ender_stone_pressure_plate");
    public static final PressurePlateBlock ENDER_STONE_PRESSURE_PLATE = Registry.register(
            Registries.BLOCK,
            ENDER_STONE_PRESSURE_PLATE_KEY,
            new PressurePlateBlock(BlockSetType.STONE,
                    Block.Settings.copy(Blocks.END_STONE).registryKey(ENDER_STONE_PRESSURE_PLATE_KEY))
    );
    public static final RegistryKey<Item> ENDER_STONE_PRESSURE_PLATE_ITEM_KEY = itemKey("ender_stone_pressure_plate");
    public static final BlockItem ENDER_STONE_PRESSURE_PLATE_ITEM = Registry.register(
            Registries.ITEM,
            ENDER_STONE_PRESSURE_PLATE_ITEM_KEY,
            new BlockItem(ENDER_STONE_PRESSURE_PLATE,
                    new Item.Settings().registryKey(ENDER_STONE_PRESSURE_PLATE_ITEM_KEY))
    );

    // ===== 幻影之石系列 / Phantom Stone series =====

    public static final RegistryKey<Block> PHANTOM_STONE_STAIRS_KEY = key("phantom_stone_stairs");
    public static final StairsBlock PHANTOM_STONE_STAIRS = Registry.register(
            Registries.BLOCK,
            PHANTOM_STONE_STAIRS_KEY,
            new StairsBlock(ModBlocks.PHANTOM_STONE.getDefaultState(),
                    Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_STONE_STAIRS_KEY))
    );
    public static final RegistryKey<Item> PHANTOM_STONE_STAIRS_ITEM_KEY = itemKey("phantom_stone_stairs");
    public static final BlockItem PHANTOM_STONE_STAIRS_ITEM = Registry.register(
            Registries.ITEM,
            PHANTOM_STONE_STAIRS_ITEM_KEY,
            new BlockItem(PHANTOM_STONE_STAIRS, new Item.Settings().registryKey(PHANTOM_STONE_STAIRS_ITEM_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_STONE_WALL_KEY = key("phantom_stone_wall");
    public static final WallBlock PHANTOM_STONE_WALL = Registry.register(
            Registries.BLOCK,
            PHANTOM_STONE_WALL_KEY,
            new WallBlock(Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_STONE_WALL_KEY))
    );
    public static final RegistryKey<Item> PHANTOM_STONE_WALL_ITEM_KEY = itemKey("phantom_stone_wall");
    public static final BlockItem PHANTOM_STONE_WALL_ITEM = Registry.register(
            Registries.ITEM,
            PHANTOM_STONE_WALL_ITEM_KEY,
            new BlockItem(PHANTOM_STONE_WALL, new Item.Settings().registryKey(PHANTOM_STONE_WALL_ITEM_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_STONE_FENCE_KEY = key("phantom_stone_fence");
    public static final FenceBlock PHANTOM_STONE_FENCE = Registry.register(
            Registries.BLOCK,
            PHANTOM_STONE_FENCE_KEY,
            new FenceBlock(Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_STONE_FENCE_KEY))
    );
    public static final RegistryKey<Item> PHANTOM_STONE_FENCE_ITEM_KEY = itemKey("phantom_stone_fence");
    public static final BlockItem PHANTOM_STONE_FENCE_ITEM = Registry.register(
            Registries.ITEM,
            PHANTOM_STONE_FENCE_ITEM_KEY,
            new BlockItem(PHANTOM_STONE_FENCE, new Item.Settings().registryKey(PHANTOM_STONE_FENCE_ITEM_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_STONE_FENCE_GATE_KEY = key("phantom_stone_fence_gate");
    public static final FenceGateBlock PHANTOM_STONE_FENCE_GATE = Registry.register(
            Registries.BLOCK,
            PHANTOM_STONE_FENCE_GATE_KEY,
            new FenceGateBlock(WoodType.OAK,
                    Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_STONE_FENCE_GATE_KEY))
    );
    public static final RegistryKey<Item> PHANTOM_STONE_FENCE_GATE_ITEM_KEY = itemKey("phantom_stone_fence_gate");
    public static final BlockItem PHANTOM_STONE_FENCE_GATE_ITEM = Registry.register(
            Registries.ITEM,
            PHANTOM_STONE_FENCE_GATE_ITEM_KEY,
            new BlockItem(PHANTOM_STONE_FENCE_GATE, new Item.Settings().registryKey(PHANTOM_STONE_FENCE_GATE_ITEM_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_STONE_BUTTON_KEY = key("phantom_stone_button");
    public static final ButtonBlock PHANTOM_STONE_BUTTON = Registry.register(
            Registries.BLOCK,
            PHANTOM_STONE_BUTTON_KEY,
            new ButtonBlock(BlockSetType.STONE, 20,
                    Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_STONE_BUTTON_KEY))
    );
    public static final RegistryKey<Item> PHANTOM_STONE_BUTTON_ITEM_KEY = itemKey("phantom_stone_button");
    public static final BlockItem PHANTOM_STONE_BUTTON_ITEM = Registry.register(
            Registries.ITEM,
            PHANTOM_STONE_BUTTON_ITEM_KEY,
            new BlockItem(PHANTOM_STONE_BUTTON, new Item.Settings().registryKey(PHANTOM_STONE_BUTTON_ITEM_KEY))
    );

    public static final RegistryKey<Block> PHANTOM_STONE_PRESSURE_PLATE_KEY = key("phantom_stone_pressure_plate");
    public static final PressurePlateBlock PHANTOM_STONE_PRESSURE_PLATE = Registry.register(
            Registries.BLOCK,
            PHANTOM_STONE_PRESSURE_PLATE_KEY,
            new PressurePlateBlock(BlockSetType.STONE,
                    Block.Settings.copy(Blocks.END_STONE).registryKey(PHANTOM_STONE_PRESSURE_PLATE_KEY))
    );
    public static final RegistryKey<Item> PHANTOM_STONE_PRESSURE_PLATE_ITEM_KEY = itemKey("phantom_stone_pressure_plate");
    public static final BlockItem PHANTOM_STONE_PRESSURE_PLATE_ITEM = Registry.register(
            Registries.ITEM,
            PHANTOM_STONE_PRESSURE_PLATE_ITEM_KEY,
            new BlockItem(PHANTOM_STONE_PRESSURE_PLATE,
                    new Item.Settings().registryKey(PHANTOM_STONE_PRESSURE_PLATE_ITEM_KEY))
    );

    private ModDecorativeBlocks() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册装饰方块完成。");
    }

    private static RegistryKey<Block> key(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, name));
    }

    private static RegistryKey<Item> itemKey(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, name));
    }
}
