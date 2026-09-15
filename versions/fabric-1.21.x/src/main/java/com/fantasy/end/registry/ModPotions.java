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
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public final class ModPotions {

    public static final RegistryKey<Potion> TELEPORT_POTION_KEY =
            RegistryKey.of(RegistryKeys.POTION, Identifier.of(FantasyTheEnd.MOD_ID, "teleport_potion"));

    public static final RegistryEntry.Reference<Potion> TELEPORT_POTION = Registry.registerReference(
            Registries.POTION,
            TELEPORT_POTION_KEY,
            new Potion("teleport", new StatusEffectInstance(ModStatusEffects.TELEPORT, 3600, 0))
    );

    public static final RegistryKey<Potion> TELEPORT_POTION_LONG_KEY =
            RegistryKey.of(RegistryKeys.POTION, Identifier.of(FantasyTheEnd.MOD_ID, "teleport_potion_long"));

    public static final RegistryEntry.Reference<Potion> TELEPORT_POTION_LONG = Registry.registerReference(
            Registries.POTION,
            TELEPORT_POTION_LONG_KEY,
            new Potion("teleport_long", new StatusEffectInstance(ModStatusEffects.TELEPORT, 9600, 0))
    );

    public static final RegistryKey<Potion> TELEPORT_POTION_STRONG_KEY =
            RegistryKey.of(RegistryKeys.POTION, Identifier.of(FantasyTheEnd.MOD_ID, "teleport_potion_strong"));

    public static final RegistryEntry.Reference<Potion> TELEPORT_POTION_STRONG = Registry.registerReference(
            Registries.POTION,
            TELEPORT_POTION_STRONG_KEY,
            new Potion("teleport_strong", new StatusEffectInstance(ModStatusEffects.TELEPORT, 1800, 1))
    );

    private ModPotions() {
    }

    public static void init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.AWKWARD, Items.CHORUS_FRUIT, TELEPORT_POTION);
            builder.registerPotionRecipe(TELEPORT_POTION, Items.REDSTONE, TELEPORT_POTION_LONG);
            builder.registerPotionRecipe(TELEPORT_POTION, Items.GLOWSTONE_DUST, TELEPORT_POTION_STRONG);
        });
        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册药水与酿造配方完成。");
    }
}
