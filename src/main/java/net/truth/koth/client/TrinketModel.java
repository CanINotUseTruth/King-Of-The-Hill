package net.truth.koth.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class TrinketModel extends BipedEntityModel<LivingEntity> {

    public TrinketModel(ModelPart root) {
        super(root);
        this.setVisible(false);
        this.head.visible = true;
    }

    // Make this match Blockbench model
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0f);
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-3f, -12f, -3f, 6f, 3f, 6f), ModelTransform.NONE);
        head.addChild("main", ModelPartBuilder.create()
                .uv(0, 9)
                .cuboid(-2f, -14f, -2f, 4f, 2f, 4f), ModelTransform.NONE);
        head.addChild("gem_top", ModelPartBuilder.create()
                .uv(0, 15)
                .cuboid(-1f, -18f, -1f, 2f, 2f, 2f), ModelTransform.NONE);
        head.addChild("point_top", ModelPartBuilder.create()
                .uv(0, 19)
                .cuboid(-0.5f, -16f, -0.5f, 1f, 1f, 1f), ModelTransform.NONE);
        head.addChild("gem_front", ModelPartBuilder.create()
                .uv(0, 15)
                .cuboid(-1f, -12f, -4f, 2f, 2f, 2f), ModelTransform.NONE);
        head.addChild("stem_north", ModelPartBuilder.create()
                .uv(16, 9)
                .cuboid(-1f, -14f, -3f, 2f, 2f, 1f), ModelTransform.NONE);
        head.addChild("stem_south", ModelPartBuilder.create()
                .uv(16, 12)
                .cuboid(-1f, -14f, 2f, 2f, 2f, 1f), ModelTransform.NONE);
        head.addChild("stem_east", ModelPartBuilder.create()
                .uv(22, 9)
                .cuboid(-3f, -14f, -1f, 1f, 2f, 2f), ModelTransform.NONE);
        head.addChild("stem_west", ModelPartBuilder.create()
                .uv(22, 13)
                .cuboid(2f, -14f, -1f, 1f, 2f, 2f), ModelTransform.NONE);
        head.addChild("top_n2s", ModelPartBuilder.create()
                .uv(14, 17)
                .cuboid(-1f, -15f, -3f, 2f, 1f, 6f), ModelTransform.NONE);
        head.addChild("top_east", ModelPartBuilder.create()
                .uv(14, 24)
                .cuboid(-3f, -15f, -1f, 2f, 1f, 2f), ModelTransform.NONE);
        head.addChild("top_west", ModelPartBuilder.create()
                .uv(22, 24)
                .cuboid(1f, -15f, -1f, 2f, 1f, 2f), ModelTransform.NONE);
        head.addChild("point_ne", ModelPartBuilder.create()
                .uv(0, 21)
                .cuboid(-3f, -13f, -3f, 1f, 1f, 1f), ModelTransform.NONE);
        head.addChild("point_nw", ModelPartBuilder.create()
                .uv(4, 21)
                .cuboid(-3f, -13f, 2f, 1f, 1f, 1f), ModelTransform.NONE);
        head.addChild("point_se", ModelPartBuilder.create()
                .uv(4, 19)
                .cuboid(2f, -13f, -3f, 1f, 1f, 1f), ModelTransform.NONE);
        head.addChild("point_sw", ModelPartBuilder.create()
                .uv(0, 21)
                .cuboid(2f, -13f, 2f, 1f, 1f, 1f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }
}