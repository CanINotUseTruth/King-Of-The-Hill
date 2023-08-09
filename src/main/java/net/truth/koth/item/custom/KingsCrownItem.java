package net.truth.koth.item.custom;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class KingsCrownItem extends TrinketItem {

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
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        KingsCompassItem.setTargetEntity(entity);
        if(entity.hasStatusEffect(StatusEffects.GLOWING)) return;
        StatusEffectInstance effectInstance = new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0, false, false);
        if(entity.world.isClient) effectInstance.setPermanent(true);
        entity.addStatusEffect(effectInstance);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        KingsCompassItem.removeTargetEntity();
        if(!entity.hasStatusEffect(StatusEffects.GLOWING)) return;
        entity.removeStatusEffect(StatusEffects.GLOWING);
    }
}
