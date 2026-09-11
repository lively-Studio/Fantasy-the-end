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