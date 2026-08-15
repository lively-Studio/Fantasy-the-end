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
package com.fantasy.end.screen;

import com.fantasy.end.entity.TameableEnderManEntity;
import com.fantasy.end.registry.ModScreenHandlers;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.slot.Slot;

/**
 * 末影人背包屏幕处理器
 * <p>
 * 布局：
 * - 末影人背包 54 格 (6x9)
 * - 盔甲槽 4 格 (头盔、胸甲、护腿、靴子)
 * - 玩家背包 27 格 (3x9)
 * - 快捷栏 9 格
 */
public class EnderManScreenHandler extends ScreenHandler {

    private static final int INVENTORY_START = 0;
    private static final int INVENTORY_END = 53;
    private static final int ARMOR_START = 54;
    private static final int ARMOR_END = 57;
    private static final int PLAYER_INVENTORY_START = 58;
    private static final int PLAYER_INVENTORY_END = 84;
    private static final int HOTBAR_START = 85;
    private static final int HOTBAR_END = 93;

    private final Inventory inventory;
    private final TameableEnderManEntity enderman;
    private final Property entityIdProperty = Property.create();

    // 客户端构造（ScreenHandlerType 工厂使用）
    public EnderManScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    // 服务端构造
    public EnderManScreenHandler(int syncId, PlayerInventory playerInventory, TameableEnderManEntity enderman) {
        super(ModScreenHandlers.ENDER_MAN, syncId);
        this.enderman = enderman;
        this.inventory = enderman != null ? enderman.getInventory() : new SimpleInventory(58);
        if (enderman != null) {
            this.entityIdProperty.set(enderman.getId());
        }
        this.addProperty(this.entityIdProperty);

        // ===== 末影人背包 54 格 (6x9) =====
        // 从左侧开始，占据主要区域
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 9; col++) {
                int index = col + row * 9;
                int x = 8 + col * 18;
                int y = 18 + row * 18;
                this.addSlot(new Slot(this.inventory, index, x, y));
            }
        }

        // ===== 盔甲槽 4 格 (垂直排列在右侧) =====
        // 索引 54=头盔, 55=胸甲, 56=护腿, 57=靴子
        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };
        for (int i = 0; i < 4; i++) {
            final EquipmentSlot slotType = armorSlots[i];
            int x = 188;
            int y = 18 + i * 18;
            int armorIndex = ARMOR_START + i;
            this.addSlot(new Slot(this.inventory, armorIndex, x, y) {
                @Override
                public boolean canInsert(ItemStack stack) {
                    if (stack.isEmpty()) return false;
                    EquippableComponent equippable = stack.get(DataComponentTypes.EQUIPPABLE);
                    return equippable != null && equippable.slot() == slotType;
                }

                @Override
                public int getMaxItemCount() {
                    return 1;
                }
            });
        }

        // ===== 玩家背包 27 格 (3x9) =====
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int index = 9 + col + row * 9; // 从索引 9 开始（跳过快捷栏）
                int x = 8 + col * 18;
                int y = 140 + row * 18;
                this.addSlot(new Slot(playerInventory, index, x, y));
            }
        }

        // ===== 快捷栏 9 格 =====
        for (int col = 0; col < 9; col++) {
            int x = 8 + col * 18;
            int y = 198;
            this.addSlot(new Slot(playerInventory, col, x, y));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack stack = this.slots.get(slotIndex).getStack();
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (slotIndex >= INVENTORY_START && slotIndex <= INVENTORY_END) {
            // 从末影人背包 -> 玩家背包
            if (!this.insertItem(stack, PLAYER_INVENTORY_START, HOTBAR_END + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (slotIndex >= ARMOR_START && slotIndex <= ARMOR_END) {
            // 从盔甲槽 -> 玩家背包
            if (!this.insertItem(stack, PLAYER_INVENTORY_START, HOTBAR_END + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (slotIndex >= PLAYER_INVENTORY_START && slotIndex <= HOTBAR_END) {
            // 从玩家背包 -> 末影人背包
            EquippableComponent equippable = stack.get(DataComponentTypes.EQUIPPABLE);
            if (equippable != null) {
                // 如果是可装备物品，尝试放入盔甲槽
                EquipmentSlot slotType = equippable.slot();
                int armorIndex = switch (slotType) {
                    case HEAD -> ARMOR_START;
                    case CHEST -> ARMOR_START + 1;
                    case LEGS -> ARMOR_START + 2;
                    case FEET -> ARMOR_START + 3;
                    default -> -1;
                };
                if (armorIndex >= 0 && this.getSlot(armorIndex).getStack().isEmpty()) {
                    if (!this.insertItem(stack, armorIndex, armorIndex + 1, false)) {
                        if (!this.insertItem(stack, INVENTORY_START, INVENTORY_END + 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    return stack;
                }
            }
            // 普通物品放入末影人背包
            if (!this.insertItem(stack, INVENTORY_START, INVENTORY_END + 1, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            return stack;
        }

        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        if (this.enderman == null) return true;
        return this.enderman.isAlive() && this.enderman.distanceTo(player) <= 8.0f;
    }

    public TameableEnderManEntity getEnderman() {
        return enderman;
    }

    public int getEntityId() {
        return this.entityIdProperty.get();
    }

    public Inventory getEndermanInventory() {
        return inventory;
    }
}