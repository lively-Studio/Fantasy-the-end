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
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class ModItemGroups {

    public static final RegistryKey<ItemGroup> MAIN_KEY =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(FantasyTheEnd.MOD_ID, "main"));

    public static void register() {
        Registry.register(
                Registries.ITEM_GROUP,
                MAIN_KEY,
                ItemGroup.create(ItemGroup.Row.TOP, 0)
                        .displayName(Text.translatable("itemGroup.fantasy_the_end.main"))
                        .icon(() -> new ItemStack(ModItems.PURPLE_ENDER_PEARL))
                        .entries((displayContext, entries) -> {
                            entries.add(ModItems.PURPLE_ENDER_PEARL);
                            entries.add(ModToolArmor.ENDER_UPGRADE_SMITHING_TEMPLATE);
                            entries.add(ModToolArmor.PHANTOM_UPGRADE_SMITHING_TEMPLATE);
                            entries.add(ModToolArmor.ENDER_SWORD);
                            entries.add(ModToolArmor.ENDER_PICKAXE);
                            entries.add(ModToolArmor.ENDER_AXE);
                            entries.add(ModToolArmor.ENDER_SHOVEL);
                            entries.add(ModToolArmor.ENDER_HOE);
                            entries.add(ModToolArmor.ENDER_HELMET);
                            entries.add(ModToolArmor.ENDER_CHESTPLATE);
                            entries.add(ModToolArmor.ENDER_LEGGINGS);
                            entries.add(ModToolArmor.ENDER_BOOTS);
                            entries.add(ModToolArmor.PHANTOM_SWORD);
                            entries.add(ModToolArmor.PHANTOM_PICKAXE);
                            entries.add(ModToolArmor.PHANTOM_AXE);
                            entries.add(ModToolArmor.PHANTOM_SHOVEL);
                            entries.add(ModToolArmor.PHANTOM_HOE);
                            entries.add(ModToolArmor.PHANTOM_HELMET);
                            entries.add(ModToolArmor.PHANTOM_CHESTPLATE);
                            entries.add(ModToolArmor.PHANTOM_LEGGINGS);
                            entries.add(ModToolArmor.PHANTOM_BOOTS);
                            entries.add(ModPlants.ENDER_FLOWER_ITEM);
                            entries.add(ModPlants.PHANTOM_FLOWER_ITEM);
                            entries.add(ModPlants.CRYSTAL_GRASS_ITEM);
                            entries.add(ModPlants.ENDER_VINE_ITEM);
                            entries.add(ModItems.ENDER_ORE);
                            entries.add(ModItems.PHANTOM_ORE);
                            entries.add(ModItems.ENDER_SLAB);
                            entries.add(ModItems.PHANTOM_SLAB);
                            entries.add(ModItems.ENDER_STONE);
                            entries.add(ModItems.PHANTOM_STONE);
                            entries.add(ModItems.ENDER_STONE_SLAB);
                            entries.add(ModItems.PHANTOM_STONE_SLAB);
                            entries.add(ModItems.ENDER_ROD);
                            entries.add(ModItems.PHANTOM_ROD);
                            entries.add(ModItems.ENDER_DOOR);
                            entries.add(ModItems.PHANTOM_DOOR);
                            entries.add(ModItems.ENDER_TRAPDOOR);
                            entries.add(ModItems.PHANTOM_TRAPDOOR);
                            entries.add(ModItems.END_METEORITE);
                            entries.add(ModItems.ENDER_FRUIT);
                            entries.add(ModItems.PHANTOM_FRUIT);
                            entries.add(ModItems.ROASTED_CHORUS_FRUIT);
                            entries.add(ModItems.ENDER_PIE);
                            entries.add(ModItems.ENDER_BACKPACK);
                            entries.add(ModItems.PHANTOM_BACKPACK);
                            entries.add(ModDecorativeBlocks.ENDER_STONE_STAIRS_ITEM);
                            entries.add(ModDecorativeBlocks.ENDER_STONE_WALL_ITEM);
                            entries.add(ModDecorativeBlocks.ENDER_STONE_FENCE_ITEM);
                            entries.add(ModDecorativeBlocks.ENDER_STONE_FENCE_GATE_ITEM);
                            entries.add(ModDecorativeBlocks.ENDER_STONE_BUTTON_ITEM);
                            entries.add(ModDecorativeBlocks.ENDER_STONE_PRESSURE_PLATE_ITEM);
                            entries.add(ModDecorativeBlocks.PHANTOM_STONE_STAIRS_ITEM);
                            entries.add(ModDecorativeBlocks.PHANTOM_STONE_WALL_ITEM);
                            entries.add(ModDecorativeBlocks.PHANTOM_STONE_FENCE_ITEM);
                            entries.add(ModDecorativeBlocks.PHANTOM_STONE_FENCE_GATE_ITEM);
                            entries.add(ModDecorativeBlocks.PHANTOM_STONE_BUTTON_ITEM);
                            entries.add(ModDecorativeBlocks.PHANTOM_STONE_PRESSURE_PLATE_ITEM);
                        })
                        .build()
        );
    }

    private ModItemGroups() {
    }
}
