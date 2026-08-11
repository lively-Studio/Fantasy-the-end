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
package com.fantasy.end;

import com.fantasy.end.registry.ModItemGroups;
import com.fantasy.end.registry.ModItems;
import com.fantasy.end.registry.ModRecipes;
import com.fantasy.end.registry.ModStatusEffects;
import com.fantasy.end.event.EnderPortalHandler;
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
        ModItems.init();
        ModItemGroups.register();
        ModRecipes.init();

        ServerTickEvents.END_SERVER_TICK.register(new EnderPortalHandler());

        LOGGER.info("[幻想:末地] 模组初始化完成——末地的新篇章即将开启。");
    }
}
