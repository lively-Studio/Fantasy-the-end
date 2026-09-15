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
import com.fantasy.end.effect.EnderStatusEffect;
import com.fantasy.end.effect.TeleportStatusEffect;
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

    public static final RegistryKey<StatusEffect> TELEPORT_KEY =
            RegistryKey.of(RegistryKeys.STATUS_EFFECT, Identifier.of(FantasyTheEnd.MOD_ID, "teleport"));

    public static final RegistryEntry.Reference<StatusEffect> TELEPORT = Registry.registerReference(
            Registries.STATUS_EFFECT,
            TELEPORT_KEY,
            new TeleportStatusEffect(StatusEffectCategory.NEUTRAL, 0x598188)
    );

    private ModStatusEffects() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册状态效果完成。");
    }
}
