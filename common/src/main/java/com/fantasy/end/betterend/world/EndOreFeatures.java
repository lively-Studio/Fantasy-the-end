/*
 * MIT License
 *
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
 *
 * ----------------------------------------------------------------------------
 * 派生声明 / Derivative notice:
 * 改编自 BetterEnd (https://github.com/quiqueck/BetterEnd, MIT, (c) 2020 paulevsGitch)。
 * Fantasy: The End 是 BetterEnd 的二次开发（衍生作品）。
 */
package com.fantasy.end.betterend.world;

import com.fantasy.end.betterend.registry.BetterEndBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.util.OreConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

/** 末地矿石生成（改编自 BetterEnd 的末地矿石分布），双版本通用。 */
public final class EndOreFeatures {
    private static final String MOD_ID = "fantasy_the_end";

    public static final RegistryKey<ConfiguredFeature<?, ?>> END_ORE_CONFIGURED =
            RegistryKey.of(Registries.CONFIGURED_FEATURE, Identifier.of(MOD_ID, "end_ore"));
    public static final RegistryKey<PlacedFeature> END_ORE_PLACED =
            RegistryKey.of(Registries.PLACED_FEATURE, Identifier.of(MOD_ID, "end_ore"));

    private EndOreFeatures() {
    }

    /** 注册 configured/placed feature，并注入到末地生物群系。 */
    public static void register() {
        OreFeatureConfig config = new OreFeatureConfig(
                List.of(OreConfiguredFeatures.END_STONE),
                BetterEndBlocks.THALLASSIUM_ORE.getDefaultState(),
                4
        );
        ConfiguredFeature<?, ?> configured = new ConfiguredFeature<>(Feature.ORE, config);
        Registry.register(Registries.CONFIGURED_FEATURE, END_ORE_CONFIGURED, configured);

        PlacedFeature placed = new PlacedFeature(
                RegistryEntry.of(configured),
                List.of(
                        CountPlacementModifier.of(6),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(
                                net.minecraft.world.gen.heightprovider.VerticalSpaceHeightType.WORLD_SURFACE_WG,
                                HeightRangePlacementModifier.BaseHeightProvider.of(0, 80)
                        )
                )
        );
        Registry.register(Registries.PLACED_FEATURE, END_ORE_PLACED, placed);

        BiomeModifications.addFeature(
                EndOreFeatures::isEndBiome,
                GenerationStep.Feature.UNDERGROUND_ORES,
                END_ORE_PLACED
        );
    }

    private static boolean isEndBiome(BiomeSelectionContext ctx) {
        return ctx.getBiome().getCategory() == Biome.Category.THE_END;
    }
}
