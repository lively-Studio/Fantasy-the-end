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
import com.fantasy.end.effect.EnderStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public final class ModStatusEffects {

    public static final RegistryKey<StatusEffect> ENDER_KEY =
            RegistryKey.of(RegistryKeys.STATUS_EFFECT, Identifier.of(FantasyTheEnd.MOD_ID, "ender"));

    public static final RegistryEntry.Reference<StatusEffect> ENDER = Registry.registerReference(
            Registries.STATUS_EFFECT,
            ENDER_KEY,
            new EnderStatusEffect(StatusEffectCategory.BENEFICIAL, 0x964BB5)
    );

    private ModStatusEffects() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册状态效果完成。");
    }
}
