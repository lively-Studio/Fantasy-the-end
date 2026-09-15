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

package com.fantasy.end;

import com.fantasy.end.network.BackpackNetworking;
import com.fantasy.end.network.EnderManMobProvider;
import com.fantasy.end.registry.ModBlocks;
import com.fantasy.end.registry.ModDecorativeBlocks;
import com.fantasy.end.registry.ModEntities;
import com.fantasy.end.registry.ModFeatures;
import com.fantasy.end.registry.ModItemGroups;
import com.fantasy.end.registry.ModItems;
import com.fantasy.end.registry.ModPotions;
import com.fantasy.end.registry.ModRecipes;
import com.fantasy.end.registry.ModScreenHandlers;
import com.fantasy.end.registry.ModStatusEffects;
import com.fantasy.end.registry.ModToolArmor;
import com.fantasy.end.event.EnderPortalHandler;
import com.fantasy.end.handler.EnderTeleportHandler;
import com.fantasy.end.handler.MeteorShowerHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FantasyTheEnd implements ModInitializer {
    public static final String MOD_ID = "fantasy_the_end";
    public static final Logger LOGGER = LoggerFactory.getLogger("Fantasy: The End");

    @Override
    public void onInitialize() {
        ModStatusEffects.init();
        ModEntities.init();
        ModFeatures.register();
        ModBlocks.init();
        ModItems.init();
        ModToolArmor.init();
        ModDecorativeBlocks.init();
        ModItemGroups.register();
        ModRecipes.init();
        ModPotions.init();
        ModScreenHandlers.init();
        BackpackNetworking.init();
        EnderManMobProvider.init();

        ServerTickEvents.END_SERVER_TICK.register(new EnderPortalHandler());
        EnderTeleportHandler.register();
        MeteorShowerHandler.register();

        LOGGER.info("[幻想:末地] 模组初始化完成——末地的新篇章即将开启。");
    }
}
