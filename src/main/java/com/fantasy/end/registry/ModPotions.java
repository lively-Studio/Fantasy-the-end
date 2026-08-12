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
            builder.registerPotionRecipe(Potions.AWKWARD, ModPlants.ENDER_FLOWER_ITEM, TELEPORT_POTION);
            builder.registerPotionRecipe(TELEPORT_POTION, Items.REDSTONE, TELEPORT_POTION_LONG);
            builder.registerPotionRecipe(TELEPORT_POTION, Items.GLOWSTONE_DUST, TELEPORT_POTION_STRONG);
        });
        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册药水与酿造配方完成。");
    }
}
