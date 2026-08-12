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
package com.fantasy.end.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class TeleportStatusEffect extends StatusEffect {

    public TeleportStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity.age % 20 == 0) {
            double baseRange = 5.0 + amplifier * 3.0;
            double dx = (world.getRandom().nextDouble() - 0.5) * 2.0 * baseRange;
            double dy = (world.getRandom().nextDouble() - 0.5) * 2.0 * (baseRange / 2.0);
            double dz = (world.getRandom().nextDouble() - 0.5) * 2.0 * baseRange;
            double targetX = entity.getX() + dx;
            double targetY = entity.getY() + dy;
            double targetZ = entity.getZ() + dz;
            entity.requestTeleport(targetX, targetY, targetZ);
            world.spawnParticles(
                    net.minecraft.particle.ParticleTypes.REVERSE_PORTAL,
                    targetX, targetY + 1.0, targetZ,
                    12, 0.5, 1.0, 0.5, 0.05
            );
        }
        return true;
    }
}
