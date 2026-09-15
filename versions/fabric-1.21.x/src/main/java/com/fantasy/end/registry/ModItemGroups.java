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
                            entries.add(ModItems.ENDER_MAN_DOLL);
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
