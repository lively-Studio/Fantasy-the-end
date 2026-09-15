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

import com.fantasy.end.entity.TameableEnderManEntity;
import com.fantasy.end.registry.ModEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 末影人玩偶物品：右键召唤回一只已驯服的末影人（保留主人绑定）。
 * 反向操作：右键已驯服末影人即可收成该玩偶。
 */
public class EndermanDollItem extends Item {

    public EndermanDollItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        TameableEnderManEntity enderman = ModEntities.TAMEABLE_ENDER_MAN.create(world, SpawnReason.MOB_SUMMONED);
        if (enderman != null) {
            // 在玩家面前召唤
            Vec3d pos = user.getBlockPos().toCenterPos().add(user.getRotationVec(1.0F).multiply(1.5));
            enderman.updatePositionAndAngles(pos.x, pos.y, pos.z, user.getYaw(), 0.0F);
            enderman.tame(user);
            world.spawnEntity(enderman);
            user.getStackInHand(hand).decrement(1);
        }
        return ActionResult.SUCCESS;
    }
}