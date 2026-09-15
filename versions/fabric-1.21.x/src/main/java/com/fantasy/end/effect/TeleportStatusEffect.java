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
