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
package com.fantasy.end.client.entity.model;

import com.fantasy.end.entity.TameableEnderManEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.state.EndermanEntityRenderState;

/**
 * 可驯服末影人实体模型
 * 复用原版末影人模型
 */
public class TameableEnderManEntityModel extends EndermanEntityModel<EndermanEntityRenderState> {

    public TameableEnderManEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(EndermanEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64);
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation, float offset) {
        return TexturedModelData.of(EndermanEntityModel.getModelData(dilation, offset), 64, 64);
    }
}