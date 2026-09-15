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