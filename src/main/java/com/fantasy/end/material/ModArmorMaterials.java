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
package com.fantasy.end.material;

import com.fantasy.end.FantasyTheEnd;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

public final class ModArmorMaterials {

    public static final TagKey<Item> ENDER_REPAIR_TAG = TagKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_armor_repair_materials"));

    public static final RegistryKey<EquipmentAsset> ENDER_TRIM_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(FantasyTheEnd.MOD_ID, "ender"));

    public static final ArmorMaterial ENDER = new ArmorMaterial(
            20,
            Map.of(
                    EquipmentType.HELMET, 4,
                    EquipmentType.CHESTPLATE, 9,
                    EquipmentType.LEGGINGS, 7,
                    EquipmentType.BOOTS, 4
            ),
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            2.0F,
            0.0F,
            ENDER_REPAIR_TAG,
            ENDER_TRIM_KEY
    );

    public static final TagKey<Item> PHANTOM_REPAIR_TAG = TagKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_armor_repair_materials"));

    public static final RegistryKey<EquipmentAsset> PHANTOM_TRIM_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(FantasyTheEnd.MOD_ID, "phantom"));

    public static final ArmorMaterial PHANTOM = new ArmorMaterial(
            20,
            Map.of(
                    EquipmentType.HELMET, 4,
                    EquipmentType.CHESTPLATE, 9,
                    EquipmentType.LEGGINGS, 7,
                    EquipmentType.BOOTS, 4
            ),
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            2.0F,
            0.0F,
            PHANTOM_REPAIR_TAG,
            PHANTOM_TRIM_KEY
    );

    private ModArmorMaterials() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册盔甲材料完成。");
    }
}
