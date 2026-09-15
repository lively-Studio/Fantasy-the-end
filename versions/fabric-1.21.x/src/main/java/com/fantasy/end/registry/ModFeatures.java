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

import com.fantasy.end.world.EnderIslandDecoratorFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
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

/**
 * Phase 1.1/1.3b：世界生成 Feature 注册骨架。
 * 代码侧仅注册自定义 Feature 类型；configured_feature / placed_feature 由数据包 JSON
 * 提供（1.21.11 动态注册表的标准做法），再通过 Fabric BiomeModifications 安全挂载到
 * 末地生物群系（不覆盖原版 biome JSON，规避 1.21.11 数据格式差异）。
 */
public final class ModFeatures {

    public static final Identifier ENDER_ISLAND_DECORATOR =
            Identifier.of("fantasy_the_end", "ender_island_decorator");

    public static void register() {
        // 1) 注册 Feature 类型（codec 由 configured_feature JSON 引用）
        Registry.register(Registries.FEATURE, ENDER_ISLAND_DECORATOR,
                new EnderIslandDecoratorFeature(DefaultFeatureConfig.CODEC));

        // 2) 挂载 placed_feature 到末地生物群系
        RegistryKey<PlacedFeature> placedKey =
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, ENDER_ISLAND_DECORATOR);
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.THE_END),
                GenerationStep.Feature.SURFACE_STRUCTURES,
                placedKey);
    }

    private ModFeatures() {
    }
}