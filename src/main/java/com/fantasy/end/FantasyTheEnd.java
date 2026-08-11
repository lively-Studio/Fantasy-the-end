package com.fantasy.end;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FantasyTheEnd implements ModInitializer {
    public static final String MOD_ID = "fantasy_the_end";
    public static final Logger LOGGER = LoggerFactory.getLogger("Fantasy: The End");

    @Override
    public void onInitialize() {
        LOGGER.info("[幻想:末地] 模组正在初始化——末地的新篇章即将开启。");
    }
}
