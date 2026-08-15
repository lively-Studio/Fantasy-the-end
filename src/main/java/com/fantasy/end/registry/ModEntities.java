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
import com.fantasy.end.entity.TameableEnderManEntity;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    @SuppressWarnings("unchecked")
    public static final RegistryKey<EntityType<TameableEnderManEntity>> TAMEABLE_ENDER_MAN_KEY =
            (RegistryKey<EntityType<TameableEnderManEntity>>)(RegistryKey<?>)RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FantasyTheEnd.MOD_ID, "tameable_enderman"));

    public static final EntityType<TameableEnderManEntity> TAMEABLE_ENDER_MAN =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    (RegistryKey<EntityType<?>>)(RegistryKey<?>)TAMEABLE_ENDER_MAN_KEY,
                    EntityType.Builder.create(TameableEnderManEntity::new, SpawnGroup.CREATURE)
                            .dimensions(0.6f, 2.9f)
                            .maxTrackingRange(32)
                            .trackingTickInterval(3)
                            .build((RegistryKey<EntityType<?>>)(RegistryKey<?>)TAMEABLE_ENDER_MAN_KEY)
            );

    // 生成蛋
    public static final RegistryKey<Item> TAMEABLE_ENDER_MAN_SPAWN_EGG_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "tameable_enderman_spawn_egg"));

    // 末影人生成蛋颜色：主色=深紫(0x2D0050), 副色=亮紫(0x9B30FF)
    public static final Item TAMEABLE_ENDER_MAN_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            TAMEABLE_ENDER_MAN_SPAWN_EGG_KEY,
            new SpawnEggItem(new Item.Settings().registryKey(TAMEABLE_ENDER_MAN_SPAWN_EGG_KEY))
    );

    public static void init() {
        // 将生成蛋添加到创造模式物品栏的"战斗"分类
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(TAMEABLE_ENDER_MAN_SPAWN_EGG);
        });

        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册实体完成。");
    }
}