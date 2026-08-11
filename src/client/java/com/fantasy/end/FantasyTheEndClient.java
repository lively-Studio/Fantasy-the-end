package com.fantasy.end;

import net.fabricmc.api.ClientModInitializer;

public class FantasyTheEndClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FantasyTheEnd.LOGGER.info("[幻想:末地] 客户端初始化完成。");
    }
}
