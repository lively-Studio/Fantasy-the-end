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
import com.fantasy.end.screen.BackpackScreenHandler;
import com.fantasy.end.screen.EnderManScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public final class ModScreenHandlers {
    public static final ScreenHandlerType<BackpackScreenHandler> BACKPACK =
            new ScreenHandlerType<>((syncId, inv) -> new BackpackScreenHandler(syncId, inv, ItemStack.EMPTY), FeatureSet.of(FeatureFlags.VANILLA));

    public static final ScreenHandlerType<EnderManScreenHandler> ENDER_MAN =
            new ScreenHandlerType<>(EnderManScreenHandler::new, FeatureSet.of(FeatureFlags.VANILLA));

    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER,
                Identifier.of(FantasyTheEnd.MOD_ID, "backpack"), BACKPACK);

        Registry.register(Registries.SCREEN_HANDLER,
                Identifier.of(FantasyTheEnd.MOD_ID, "ender_man"), ENDER_MAN);

        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册容器处理器完成。");
    }

    private ModScreenHandlers() {
    }
}