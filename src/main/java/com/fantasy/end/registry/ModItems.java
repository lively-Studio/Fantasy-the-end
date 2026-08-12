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
import com.fantasy.end.item.PurpleEnderPearlItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModItems {

    public static final RegistryKey<Item> PURPLE_ENDER_PEARL_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "purple_ender_pearl"));

    public static final Item PURPLE_ENDER_PEARL = Registry.register(
            Registries.ITEM,
            PURPLE_ENDER_PEARL_KEY,
            new PurpleEnderPearlItem(new Item.Settings().registryKey(PURPLE_ENDER_PEARL_KEY))
    );

    public static final RegistryKey<Item> ENDER_ROD_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_rod"));

    public static final Item ENDER_ROD = Registry.register(
            Registries.ITEM,
            ENDER_ROD_KEY,
            new Item(new Item.Settings().registryKey(ENDER_ROD_KEY))
    );

    public static final RegistryKey<Item> PHANTOM_ROD_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_rod"));

    public static final Item PHANTOM_ROD = Registry.register(
            Registries.ITEM,
            PHANTOM_ROD_KEY,
            new Item(new Item.Settings().registryKey(PHANTOM_ROD_KEY))
    );

    public static final RegistryKey<Item> ENDER_ORE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_ore"));

    public static final BlockItem ENDER_ORE = Registry.register(
            Registries.ITEM,
            ENDER_ORE_KEY,
            new BlockItem(ModBlocks.ENDER_ORE, new Item.Settings().registryKey(ENDER_ORE_KEY))
    );

    public static final RegistryKey<Item> PHANTOM_ORE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_ore"));

    public static final BlockItem PHANTOM_ORE = Registry.register(
            Registries.ITEM,
            PHANTOM_ORE_KEY,
            new BlockItem(ModBlocks.PHANTOM_ORE, new Item.Settings().registryKey(PHANTOM_ORE_KEY))
    );

    public static final RegistryKey<Item> ENDER_SLAB_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_slab"));

    public static final BlockItem ENDER_SLAB = Registry.register(
            Registries.ITEM,
            ENDER_SLAB_KEY,
            new BlockItem(ModBlocks.ENDER_SLAB, new Item.Settings().registryKey(ENDER_SLAB_KEY))
    );

    public static final RegistryKey<Item> PHANTOM_SLAB_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_slab"));

    public static final BlockItem PHANTOM_SLAB = Registry.register(
            Registries.ITEM,
            PHANTOM_SLAB_KEY,
            new BlockItem(ModBlocks.PHANTOM_SLAB, new Item.Settings().registryKey(PHANTOM_SLAB_KEY))
    );

    public static final RegistryKey<Item> ENDER_STONE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_stone"));

    public static final BlockItem ENDER_STONE = Registry.register(
            Registries.ITEM,
            ENDER_STONE_KEY,
            new BlockItem(ModBlocks.ENDER_STONE, new Item.Settings().registryKey(ENDER_STONE_KEY))
    );

    public static final RegistryKey<Item> PHANTOM_STONE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_stone"));

    public static final BlockItem PHANTOM_STONE = Registry.register(
            Registries.ITEM,
            PHANTOM_STONE_KEY,
            new BlockItem(ModBlocks.PHANTOM_STONE, new Item.Settings().registryKey(PHANTOM_STONE_KEY))
    );

    public static final RegistryKey<Item> ENDER_STONE_SLAB_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_stone_slab"));

    public static final BlockItem ENDER_STONE_SLAB = Registry.register(
            Registries.ITEM,
            ENDER_STONE_SLAB_KEY,
            new BlockItem(ModBlocks.ENDER_STONE_SLAB, new Item.Settings().registryKey(ENDER_STONE_SLAB_KEY))
    );

    public static final RegistryKey<Item> PHANTOM_STONE_SLAB_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_stone_slab"));

    public static final BlockItem PHANTOM_STONE_SLAB = Registry.register(
            Registries.ITEM,
            PHANTOM_STONE_SLAB_KEY,
            new BlockItem(ModBlocks.PHANTOM_STONE_SLAB, new Item.Settings().registryKey(PHANTOM_STONE_SLAB_KEY))
    );

    public static final RegistryKey<Item> ENDER_DOOR_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_door"));

    public static final BlockItem ENDER_DOOR = Registry.register(
            Registries.ITEM,
            ENDER_DOOR_KEY,
            new BlockItem(ModBlocks.ENDER_DOOR, new Item.Settings().registryKey(ENDER_DOOR_KEY))
    );

    public static final RegistryKey<Item> PHANTOM_DOOR_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_door"));

    public static final BlockItem PHANTOM_DOOR = Registry.register(
            Registries.ITEM,
            PHANTOM_DOOR_KEY,
            new BlockItem(ModBlocks.PHANTOM_DOOR, new Item.Settings().registryKey(PHANTOM_DOOR_KEY))
    );

    public static final RegistryKey<Item> ENDER_TRAPDOOR_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_trapdoor"));

    public static final BlockItem ENDER_TRAPDOOR = Registry.register(
            Registries.ITEM,
            ENDER_TRAPDOOR_KEY,
            new BlockItem(ModBlocks.ENDER_TRAPDOOR, new Item.Settings().registryKey(ENDER_TRAPDOOR_KEY))
    );

    public static final RegistryKey<Item> PHANTOM_TRAPDOOR_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_trapdoor"));

    public static final BlockItem PHANTOM_TRAPDOOR = Registry.register(
            Registries.ITEM,
            PHANTOM_TRAPDOOR_KEY,
            new BlockItem(ModBlocks.PHANTOM_TRAPDOOR, new Item.Settings().registryKey(PHANTOM_TRAPDOOR_KEY))
    );

    private ModItems() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册物品完成。");
    }
}
