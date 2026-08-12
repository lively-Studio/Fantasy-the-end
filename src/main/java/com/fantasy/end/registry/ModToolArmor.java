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
import com.fantasy.end.material.ModArmorMaterials;
import com.fantasy.end.material.ModToolMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public final class ModToolArmor {

    public static final RegistryKey<Item> ENDER_UPGRADE_SMITHING_TEMPLATE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_upgrade_smithing_template"));

    public static final RegistryKey<Item> PHANTOM_UPGRADE_SMITHING_TEMPLATE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_upgrade_smithing_template"));

    public static final RegistryKey<Item> ENDER_SWORD_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_sword"));

    public static final RegistryKey<Item> ENDER_PICKAXE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_pickaxe"));

    public static final RegistryKey<Item> ENDER_AXE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_axe"));

    public static final RegistryKey<Item> ENDER_SHOVEL_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_shovel"));

    public static final RegistryKey<Item> ENDER_HOE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_hoe"));

    public static final RegistryKey<Item> PHANTOM_SWORD_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_sword"));

    public static final RegistryKey<Item> PHANTOM_PICKAXE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_pickaxe"));

    public static final RegistryKey<Item> PHANTOM_AXE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_axe"));

    public static final RegistryKey<Item> PHANTOM_SHOVEL_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_shovel"));

    public static final RegistryKey<Item> PHANTOM_HOE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_hoe"));

    public static final RegistryKey<Item> ENDER_HELMET_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_helmet"));

    public static final RegistryKey<Item> ENDER_CHESTPLATE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_chestplate"));

    public static final RegistryKey<Item> ENDER_LEGGINGS_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_leggings"));

    public static final RegistryKey<Item> ENDER_BOOTS_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_boots"));

    public static final RegistryKey<Item> PHANTOM_HELMET_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_helmet"));

    public static final RegistryKey<Item> PHANTOM_CHESTPLATE_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_chestplate"));

    public static final RegistryKey<Item> PHANTOM_LEGGINGS_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_leggings"));

    public static final RegistryKey<Item> PHANTOM_BOOTS_KEY =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_boots"));

    public static final Item ENDER_SWORD;
    public static final Item ENDER_PICKAXE;
    public static final Item ENDER_AXE;
    public static final Item ENDER_SHOVEL;
    public static final Item ENDER_HOE;
    public static final Item PHANTOM_SWORD;
    public static final Item PHANTOM_PICKAXE;
    public static final Item PHANTOM_AXE;
    public static final Item PHANTOM_SHOVEL;
    public static final Item PHANTOM_HOE;
    public static final Item ENDER_HELMET;
    public static final Item ENDER_CHESTPLATE;
    public static final Item ENDER_LEGGINGS;
    public static final Item ENDER_BOOTS;
    public static final Item PHANTOM_HELMET;
    public static final Item PHANTOM_CHESTPLATE;
    public static final Item PHANTOM_LEGGINGS;
    public static final Item PHANTOM_BOOTS;
    public static final SmithingTemplateItem ENDER_UPGRADE_SMITHING_TEMPLATE;
    public static final SmithingTemplateItem PHANTOM_UPGRADE_SMITHING_TEMPLATE;

    static {
        ENDER_SWORD = Registry.register(
                Registries.ITEM,
                ENDER_SWORD_KEY,
                new Item(new Item.Settings().registryKey(ENDER_SWORD_KEY).sword(ModToolMaterials.ENDER, 5, 1.6F))
        );

        ENDER_PICKAXE = Registry.register(
                Registries.ITEM,
                ENDER_PICKAXE_KEY,
                new Item(new Item.Settings().registryKey(ENDER_PICKAXE_KEY).pickaxe(ModToolMaterials.ENDER, 3, 1.2F))
        );

        ENDER_AXE = Registry.register(
                Registries.ITEM,
                ENDER_AXE_KEY,
                new Item(new Item.Settings().registryKey(ENDER_AXE_KEY).axe(ModToolMaterials.ENDER, 6, 1.0F))
        );

        ENDER_SHOVEL = Registry.register(
                Registries.ITEM,
                ENDER_SHOVEL_KEY,
                new Item(new Item.Settings().registryKey(ENDER_SHOVEL_KEY).shovel(ModToolMaterials.ENDER, 2.5F, 1.0F))
        );

        ENDER_HOE = Registry.register(
                Registries.ITEM,
                ENDER_HOE_KEY,
                new Item(new Item.Settings().registryKey(ENDER_HOE_KEY).hoe(ModToolMaterials.ENDER, 0, 4.0F))
        );

        PHANTOM_SWORD = Registry.register(
                Registries.ITEM,
                PHANTOM_SWORD_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_SWORD_KEY).sword(ModToolMaterials.PHANTOM, 5, 1.6F))
        );

        PHANTOM_PICKAXE = Registry.register(
                Registries.ITEM,
                PHANTOM_PICKAXE_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_PICKAXE_KEY).pickaxe(ModToolMaterials.PHANTOM, 3, 1.2F))
        );

        PHANTOM_AXE = Registry.register(
                Registries.ITEM,
                PHANTOM_AXE_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_AXE_KEY).axe(ModToolMaterials.PHANTOM, 6, 1.0F))
        );

        PHANTOM_SHOVEL = Registry.register(
                Registries.ITEM,
                PHANTOM_SHOVEL_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_SHOVEL_KEY).shovel(ModToolMaterials.PHANTOM, 2.5F, 1.0F))
        );

        PHANTOM_HOE = Registry.register(
                Registries.ITEM,
                PHANTOM_HOE_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_HOE_KEY).hoe(ModToolMaterials.PHANTOM, 0, 4.0F))
        );

        ENDER_HELMET = Registry.register(
                Registries.ITEM,
                ENDER_HELMET_KEY,
                new Item(new Item.Settings().registryKey(ENDER_HELMET_KEY).armor(ModArmorMaterials.ENDER, EquipmentType.HELMET).maxDamage(EquipmentType.HELMET.getMaxDamage(20)))
        );

        ENDER_CHESTPLATE = Registry.register(
                Registries.ITEM,
                ENDER_CHESTPLATE_KEY,
                new Item(new Item.Settings().registryKey(ENDER_CHESTPLATE_KEY).armor(ModArmorMaterials.ENDER, EquipmentType.CHESTPLATE).maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(20)))
        );

        ENDER_LEGGINGS = Registry.register(
                Registries.ITEM,
                ENDER_LEGGINGS_KEY,
                new Item(new Item.Settings().registryKey(ENDER_LEGGINGS_KEY).armor(ModArmorMaterials.ENDER, EquipmentType.LEGGINGS).maxDamage(EquipmentType.LEGGINGS.getMaxDamage(20)))
        );

        ENDER_BOOTS = Registry.register(
                Registries.ITEM,
                ENDER_BOOTS_KEY,
                new Item(new Item.Settings().registryKey(ENDER_BOOTS_KEY).armor(ModArmorMaterials.ENDER, EquipmentType.BOOTS).maxDamage(EquipmentType.BOOTS.getMaxDamage(20)))
        );

        PHANTOM_HELMET = Registry.register(
                Registries.ITEM,
                PHANTOM_HELMET_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_HELMET_KEY).armor(ModArmorMaterials.PHANTOM, EquipmentType.HELMET).maxDamage(EquipmentType.HELMET.getMaxDamage(20)))
        );

        PHANTOM_CHESTPLATE = Registry.register(
                Registries.ITEM,
                PHANTOM_CHESTPLATE_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_CHESTPLATE_KEY).armor(ModArmorMaterials.PHANTOM, EquipmentType.CHESTPLATE).maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(20)))
        );

        PHANTOM_LEGGINGS = Registry.register(
                Registries.ITEM,
                PHANTOM_LEGGINGS_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_LEGGINGS_KEY).armor(ModArmorMaterials.PHANTOM, EquipmentType.LEGGINGS).maxDamage(EquipmentType.LEGGINGS.getMaxDamage(20)))
        );

        PHANTOM_BOOTS = Registry.register(
                Registries.ITEM,
                PHANTOM_BOOTS_KEY,
                new Item(new Item.Settings().registryKey(PHANTOM_BOOTS_KEY).armor(ModArmorMaterials.PHANTOM, EquipmentType.BOOTS).maxDamage(EquipmentType.BOOTS.getMaxDamage(20)))
        );

        ENDER_UPGRADE_SMITHING_TEMPLATE = Registry.register(
                Registries.ITEM,
                ENDER_UPGRADE_SMITHING_TEMPLATE_KEY,
                new SmithingTemplateItem(
                        Text.translatable("smithing_template." + FantasyTheEnd.MOD_ID + ".ender_upgrade.applies_to"),
                        Text.translatable("smithing_template." + FantasyTheEnd.MOD_ID + ".ender_upgrade.ingredients"),
                        Text.translatable("smithing_template." + FantasyTheEnd.MOD_ID + ".ender_upgrade.base_slot_description"),
                        Text.translatable("smithing_template." + FantasyTheEnd.MOD_ID + ".ender_upgrade.additions_slot_description"),
                        List.of(
                                Identifier.ofVanilla("diamond_sword"),
                                Identifier.ofVanilla("diamond_pickaxe"),
                                Identifier.ofVanilla("diamond_axe"),
                                Identifier.ofVanilla("diamond_shovel"),
                                Identifier.ofVanilla("diamond_hoe"),
                                Identifier.ofVanilla("diamond_helmet"),
                                Identifier.ofVanilla("diamond_chestplate"),
                                Identifier.ofVanilla("diamond_leggings"),
                                Identifier.ofVanilla("diamond_boots")
                        ),
                        List.of(
                                ModItems.ENDER_STONE_KEY.getValue()
                        ),
                        new Item.Settings().registryKey(ENDER_UPGRADE_SMITHING_TEMPLATE_KEY).maxCount(16)
                )
        );

        PHANTOM_UPGRADE_SMITHING_TEMPLATE = Registry.register(
                Registries.ITEM,
                PHANTOM_UPGRADE_SMITHING_TEMPLATE_KEY,
                new SmithingTemplateItem(
                        Text.translatable("smithing_template." + FantasyTheEnd.MOD_ID + ".phantom_upgrade.applies_to"),
                        Text.translatable("smithing_template." + FantasyTheEnd.MOD_ID + ".phantom_upgrade.ingredients"),
                        Text.translatable("smithing_template." + FantasyTheEnd.MOD_ID + ".phantom_upgrade.base_slot_description"),
                        Text.translatable("smithing_template." + FantasyTheEnd.MOD_ID + ".phantom_upgrade.additions_slot_description"),
                        List.of(
                                ENDER_SWORD_KEY.getValue(),
                                ENDER_PICKAXE_KEY.getValue(),
                                ENDER_AXE_KEY.getValue(),
                                ENDER_SHOVEL_KEY.getValue(),
                                ENDER_HOE_KEY.getValue(),
                                ENDER_HELMET_KEY.getValue(),
                                ENDER_CHESTPLATE_KEY.getValue(),
                                ENDER_LEGGINGS_KEY.getValue(),
                                ENDER_BOOTS_KEY.getValue()
                        ),
                        List.of(
                                ModItems.PHANTOM_STONE_KEY.getValue()
                        ),
                        new Item.Settings().registryKey(PHANTOM_UPGRADE_SMITHING_TEMPLATE_KEY).maxCount(16)
                )
        );
    }

    private ModToolArmor() {
    }

    public static void init() {
        ModToolMaterials.init();
        ModArmorMaterials.init();
        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册工具与盔甲完成。");
    }
}
