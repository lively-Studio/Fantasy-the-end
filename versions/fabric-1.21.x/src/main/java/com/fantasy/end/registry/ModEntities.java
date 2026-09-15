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
import com.fantasy.end.entity.TameableEnderManEntity;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.TypedEntityData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;

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
            new SpawnEggItem(new Item.Settings()
                    .registryKey(TAMEABLE_ENDER_MAN_SPAWN_EGG_KEY)
                    .component(DataComponentTypes.ENTITY_DATA,
                            TypedEntityData.create(TAMEABLE_ENDER_MAN, new NbtCompound())))
    );

    public static void init() {
        // 将生成蛋添加到创造模式物品栏的"战斗"分类
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(TAMEABLE_ENDER_MAN_SPAWN_EGG);
        });

        FantasyTheEnd.LOGGER.info("[幻想:末地] 注册实体完成。");
    }
}