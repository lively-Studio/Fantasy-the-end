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
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
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

    // ===== 末地事件/陨石方块 =====

    public static final RegistryKey<Item> END_METEORITE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "end_meteorite"));

    public static final BlockItem END_METEORITE = Registry.register(
            Registries.ITEM,
            END_METEORITE_KEY,
            new BlockItem(ModBlocks.END_METEORITE, new Item.Settings().registryKey(END_METEORITE_KEY))
    );

    // ===== 末地生态/食物 =====

    public static final RegistryKey<Item> ENDER_FRUIT_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_fruit"));

    public static final Item ENDER_FRUIT = Registry.register(
            Registries.ITEM,
            ENDER_FRUIT_KEY,
            new Item(new Item.Settings()
                    .registryKey(ENDER_FRUIT_KEY)
                    .food(new FoodComponent.Builder()
                            .nutrition(4)
                            .saturationModifier(0.6F)
                            .build(),
                            ConsumableComponents.food()
                            .consumeEffect(new ApplyEffectsConsumeEffect(
                                    new StatusEffectInstance(ModStatusEffects.ENDER, 200, 0), 1.0F))
                            .build()))
    );

    public static final RegistryKey<Item> PHANTOM_FRUIT_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_fruit"));

    public static final Item PHANTOM_FRUIT = Registry.register(
            Registries.ITEM,
            PHANTOM_FRUIT_KEY,
            new Item(new Item.Settings()
                    .registryKey(PHANTOM_FRUIT_KEY)
                    .food(new FoodComponent.Builder()
                            .nutrition(4)
                            .saturationModifier(0.6F)
                            .build(),
                            ConsumableComponents.food()
                            .consumeEffect(new ApplyEffectsConsumeEffect(
                                    new StatusEffectInstance(ModStatusEffects.TELEPORT, 100, 0), 1.0F))
                            .build()))
    );

    public static final RegistryKey<Item> ROASTED_CHORUS_FRUIT_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "roasted_chorus_fruit"));

    public static final Item ROASTED_CHORUS_FRUIT = Registry.register(
            Registries.ITEM,
            ROASTED_CHORUS_FRUIT_KEY,
            new Item(new Item.Settings()
                    .registryKey(ROASTED_CHORUS_FRUIT_KEY)
                    .food(new FoodComponent.Builder()
                            .nutrition(6)
                            .saturationModifier(0.8F)
                            .build()))
    );

    public static final RegistryKey<Item> ENDER_PIE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_pie"));

    public static final Item ENDER_PIE = Registry.register(
            Registries.ITEM,
            ENDER_PIE_KEY,
            new Item(new Item.Settings()
                    .registryKey(ENDER_PIE_KEY)
                    .food(new FoodComponent.Builder()
                            .nutrition(8)
                            .saturationModifier(0.9F)
                            .build(),
                            ConsumableComponents.food()
                            .consumeEffect(new ApplyEffectsConsumeEffect(
                                    new StatusEffectInstance(ModStatusEffects.ENDER, 400, 0), 1.0F))
                            .build()))
    );

    private ModItems() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册物品完成。");
    }
}
