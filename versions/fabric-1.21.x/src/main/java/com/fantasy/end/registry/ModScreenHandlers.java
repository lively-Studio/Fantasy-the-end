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