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
package com.fantasy.end.client.screen;

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.registry.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

/**
 * 客户端屏幕注册
 */
public class ModScreens {

    public static void init() {
        HandledScreens.register(ModScreenHandlers.ENDER_MAN, EnderManScreen::new);
        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册屏幕完成。");
    }
}