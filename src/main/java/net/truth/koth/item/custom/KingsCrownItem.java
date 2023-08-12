package net.truth.koth.item.custom;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.*;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.truth.koth.KingOfTheHill;
import net.truth.koth.client.TrinketModel;


import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class KingsCrownItem extends TrinketItem implements TrinketRenderer {

    private static final Identifier TEXTURE = new Identifier(KingOfTheHill.MOD_ID, "textures/entity/trinket/crown.png");
    private BipedEntityModel<LivingEntity> model;

    public KingsCrownItem(Settings settings) {
        super(settings);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        // +250% Health
        modifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(uuid, "koth:health", 2.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        // +50% Knockback
        modifiers.put(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, new EntityAttributeModifier(uuid, "koth:knockback", 0.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        // +100% Armor
        modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "koth:armor", 1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        return modifiers;
    }

    @Override
    public TrinketEnums.DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return TrinketEnums.DropRule.KEEP;
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        CrownLocatorItem.setTargetEntity(entity);
        if(entity.hasStatusEffect(StatusEffects.GLOWING)) return;
        StatusEffectInstance effectInstance = new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0, false, false);
        if(entity.world.isClient) effectInstance.setPermanent(true);
        entity.addStatusEffect(effectInstance);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        CrownLocatorItem.removeTargetEntity();
        if(!entity.hasStatusEffect(StatusEffects.GLOWING)) return;
        entity.removeStatusEffect(StatusEffects.GLOWING);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        BipedEntityModel<LivingEntity> model = this.getModel();
        model.setAngles(entity, limbAngle, limbDistance, animationProgress, animationProgress, headPitch);
        model.animateModel(entity, limbAngle, limbDistance, tickDelta);
        TrinketRenderer.followBodyRotations(entity, model);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(model.getLayer(TEXTURE));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
    }

    @Environment(EnvType.CLIENT)
    private BipedEntityModel<LivingEntity> getModel() {
        if (this.model == null) {
            // Vanilla 1.17 uses EntityModels, EntityModelLoader and EntityModelLayers
            this.model = new TrinketModel(TrinketModel.getTexturedModelData().createModel());
        }

        return this.model;
    }
}
