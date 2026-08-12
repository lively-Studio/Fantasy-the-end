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
import com.fantasy.end.block.EnderFlowerBlock;
import com.fantasy.end.block.EnderGrassBlock;
import com.fantasy.end.world.EnderVineFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;

public final class ModPlants {

    // ===== 末影花 ender_flower =====
    public static final RegistryKey<Block> ENDER_FLOWER_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "ender_flower"));

    public static final EnderFlowerBlock ENDER_FLOWER = Registry.register(
            Registries.BLOCK,
            ENDER_FLOWER_KEY,
            new EnderFlowerBlock(StatusEffects.LEVITATION, 0.0F,
                    Block.Settings.copy(Blocks.DANDELION).registryKey(ENDER_FLOWER_KEY))
    );

    public static final RegistryKey<Item> ENDER_FLOWER_ITEM_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_flower"));

    public static final BlockItem ENDER_FLOWER_ITEM = Registry.register(
            Registries.ITEM,
            ENDER_FLOWER_ITEM_KEY,
            new BlockItem(ENDER_FLOWER, new Item.Settings().registryKey(ENDER_FLOWER_ITEM_KEY))
    );

    // ===== 幻影花 phantom_flower =====
    public static final RegistryKey<Block> PHANTOM_FLOWER_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_flower"));

    public static final EnderFlowerBlock PHANTOM_FLOWER = Registry.register(
            Registries.BLOCK,
            PHANTOM_FLOWER_KEY,
            new EnderFlowerBlock(StatusEffects.NIGHT_VISION, 0.0F,
                    Block.Settings.copy(Blocks.DANDELION).registryKey(PHANTOM_FLOWER_KEY))
    );

    public static final RegistryKey<Item> PHANTOM_FLOWER_ITEM_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_flower"));

    public static final BlockItem PHANTOM_FLOWER_ITEM = Registry.register(
            Registries.ITEM,
            PHANTOM_FLOWER_ITEM_KEY,
            new BlockItem(PHANTOM_FLOWER, new Item.Settings().registryKey(PHANTOM_FLOWER_ITEM_KEY))
    );

    // ===== 晶体草 crystal_grass =====
    public static final RegistryKey<Block> CRYSTAL_GRASS_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "crystal_grass"));

    public static final EnderGrassBlock CRYSTAL_GRASS = Registry.register(
            Registries.BLOCK,
            CRYSTAL_GRASS_KEY,
            new EnderGrassBlock(Block.Settings.copy(Blocks.SHORT_GRASS).registryKey(CRYSTAL_GRASS_KEY))
    );

    public static final RegistryKey<Item> CRYSTAL_GRASS_ITEM_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "crystal_grass"));

    public static final BlockItem CRYSTAL_GRASS_ITEM = Registry.register(
            Registries.ITEM,
            CRYSTAL_GRASS_ITEM_KEY,
            new BlockItem(CRYSTAL_GRASS, new Item.Settings().registryKey(CRYSTAL_GRASS_ITEM_KEY))
    );

    // ===== 末影藤蔓 ender_vine =====
    public static final RegistryKey<Block> ENDER_VINE_KEY =
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FantasyTheEnd.MOD_ID, "ender_vine"));

    public static final VineBlock ENDER_VINE = Registry.register(
            Registries.BLOCK,
            ENDER_VINE_KEY,
            new VineBlock(Block.Settings.copy(Blocks.VINE).registryKey(ENDER_VINE_KEY))
    );

    public static final RegistryKey<Item> ENDER_VINE_ITEM_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_vine"));

    public static final BlockItem ENDER_VINE_ITEM = Registry.register(
            Registries.ITEM,
            ENDER_VINE_ITEM_KEY,
            new BlockItem(ENDER_VINE, new Item.Settings().registryKey(ENDER_VINE_ITEM_KEY))
    );

    // ===== 自定义特性 ender_vine feature =====
    public static final RegistryKey<Feature<?>> ENDER_VINE_FEATURE_KEY =
            RegistryKey.of(RegistryKeys.FEATURE, Identifier.of(FantasyTheEnd.MOD_ID, "ender_vine"));

    public static final EnderVineFeature ENDER_VINE_FEATURE = Registry.register(
            Registries.FEATURE,
            ENDER_VINE_FEATURE_KEY,
            new EnderVineFeature(DefaultFeatureConfig.CODEC)
    );

    private ModPlants() {
    }

    public static void init() {
        addVegetalFeature("ender_flower");
        addVegetalFeature("phantom_flower");
        addVegetalFeature("crystal_grass");
        addVegetalFeature("ender_vine");

        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册末地植物完成。");
    }

    private static void addVegetalFeature(String name) {
        RegistryKey<PlacedFeature> key = RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(FantasyTheEnd.MOD_ID, name)
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS),
                GenerationStep.Feature.VEGETAL_DECORATION,
                key
        );
    }
}
