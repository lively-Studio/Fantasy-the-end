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
package com.fantasy.end.item;

import com.fantasy.end.screen.BackpackScreenHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class BackpackItem extends Item {
    public static final int BACKPACK_SIZE = 27;
    private final Text title;

    public BackpackItem(Settings settings, Text title) {
        super(settings);
        this.title = title;
    }

    public static SimpleInventory getInventory(ItemStack stack) {
        SimpleInventory inv = new SimpleInventory(BACKPACK_SIZE);
        ContainerComponent component = stack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            int i = 0;
            for (ItemStack itemStack : component.iterateNonEmpty()) {
                if (i < BACKPACK_SIZE) {
                    inv.setStack(i, itemStack);
                    i++;
                }
            }
        }
        return inv;
    }

    public static void setInventory(ItemStack stack, SimpleInventory inv) {
        stack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(inv.getHeldStacks()));
    }

    public static NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        Text title = stack.getName();
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inv, player) -> new BackpackScreenHandler(syncId, inv, stack),
                title
        );
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) return ActionResult.SUCCESS;

        user.openHandledScreen(createScreenHandlerFactory(stack));
        return ActionResult.CONSUME;
    }

    public Text getBackpackTitle() {
        return title;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> tooltip, TooltipType type) {
        ContainerComponent component = stack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            int count = (int) component.stream().filter(s -> !s.isEmpty()).count();
            tooltip.accept(Text.translatable("item.fantasy_the_end.backpack.tooltip", count, BACKPACK_SIZE));
        }
        super.appendTooltip(stack, context, displayComponent, tooltip, type);
    }
}