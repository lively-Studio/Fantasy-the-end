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

package com.fantasy.end.client.mixin;

import com.fantasy.end.FantasyTheEnd;
import com.fantasy.end.client.item.model.EndermanDollModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 把末影人玩偶的自定义 SpecialModel 类型注册到 SpecialModelTypes.ID_MAPPER，
 * 从而可以在 items/*.json 中用 "type": "fantasy_the_end:enderman_doll" 引用。
 */
@Mixin(SpecialModelTypes.class)
public abstract class SpecialModelTypesMixin {

    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void fantasy_end_registerDoll(CallbackInfo ci) {
        SpecialModelTypes.ID_MAPPER.put(
                Identifier.of(FantasyTheEnd.MOD_ID, "enderman_doll"),
                EndermanDollModelRenderer.Unbaked.CODEC
        );
    }
}