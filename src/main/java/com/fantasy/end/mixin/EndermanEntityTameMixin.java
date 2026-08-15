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
package com.fantasy.end.mixin;

import com.fantasy.end.entity.TameableEnderManEntity;
import com.fantasy.end.item.PurpleEnderPearlItem;
import com.fantasy.end.registry.ModEntities;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class EndermanEntityTameMixin {

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        // 只处理末影人（包括原版和可驯服末影人）
        if (!(((Object) this) instanceof EndermanEntity enderman)) return;

        // 可驯服末影人已有自己的处理逻辑，跳过
        if (enderman instanceof TameableEnderManEntity) return;

        World world = enderman.getEntityWorld();

        // 只在服务端处理
        if (world.isClient()) return;

        // 检查是否手持紫色末影珍珠
        if (player.getStackInHand(hand).getItem() instanceof PurpleEnderPearlItem) {
            // 在服务端执行转换
            if (world instanceof ServerWorld serverWorld) {
                // 消耗珍珠
                if (!player.isCreative()) {
                    player.getStackInHand(hand).decrement(1);
                }

                // 创建可驯服末影人实体
                TameableEnderManEntity tamedEnderman = ModEntities.TAMEABLE_ENDER_MAN.create(serverWorld, SpawnReason.CONVERSION);
                if (tamedEnderman != null) {
                    // 复制位置和朝向
                    tamedEnderman.refreshPositionAndAngles(enderman.getX(), enderman.getY(), enderman.getZ(), enderman.getYaw(), enderman.getPitch());
                    tamedEnderman.setBodyYaw(enderman.getBodyYaw());
                    tamedEnderman.setHeadYaw(enderman.getHeadYaw());

                    // 设置驯服状态
                    tamedEnderman.tame(player);

                    // 移除原版末影人，生成驯服末影人
                    enderman.discard();
                    serverWorld.spawnEntity(tamedEnderman);

                    // 发送爱心粒子（状态码 18 = 爱心粒子）
                    serverWorld.sendEntityStatus(tamedEnderman, (byte) 18);
                }
            }

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}