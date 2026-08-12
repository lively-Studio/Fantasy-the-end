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
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModToolMaterials {

    public static final TagKey<Item> ENDER_REPAIR_TAG = TagKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "ender_repair_materials"));

    public static final TagKey<Item> PHANTOM_REPAIR_TAG = TagKey.of(RegistryKeys.ITEM, Identifier.of(FantasyTheEnd.MOD_ID, "phantom_repair_materials"));

    public static final ToolMaterial ENDER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2031,
            9.0F,
            4.0F,
            20,
            ENDER_REPAIR_TAG
    );

    public static final ToolMaterial PHANTOM = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2031,
            9.0F,
            4.0F,
            20,
            PHANTOM_REPAIR_TAG
    );

    private ModToolMaterials() {
    }

    public static void init() {
        FantasyTheEnd.LOGGER.info("[幻想:末地/Fantasy:The End] 注册工具材料完成。");
    }
}
