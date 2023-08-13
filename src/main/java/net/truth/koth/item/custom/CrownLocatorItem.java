package net.truth.koth.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CrownLocatorItem extends Item {

    private static LivingEntity targetEntity = null;
    public CrownLocatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            //TODO Fix this with new implementation
            if(targetEntity != null) {
                user.sendMessage(Text.of(getTargetName() + getDivider() + getCrownPos() + getDivider() + getDimensionName()), true);
            } else {
                user.sendMessage(Text.literal("§6No King Found!"), true);
            }
        }
        user.getItemCooldownManager().set(this, 200);
        return TypedActionResult.success(this.getDefaultStack());
    }

    //TODO Fix this with new implementation
    public static String getDimensionName() {
        String dimensionKey = targetEntity.getWorld().getDimensionKey().getValue().toShortTranslationKey();
        String dimensionText;
        String dimensionColor;

        switch (dimensionKey) {
            case "the_nether" -> {
                dimensionText = "The Nether";
                dimensionColor = "§4";
            }
            case "overworld" -> {
                dimensionText = "Overworld";
                dimensionColor = "§2";
            }
            case "the_end" -> {
                dimensionText = "The End";
                dimensionColor = "§5";
            }
            default -> {
                dimensionText = dimensionKey;
                dimensionColor = "§f";
            }
        }
        return dimensionColor + dimensionText;
    }

    //TODO Fix this with new implementation
    public static String getCrownPos() {
        Vec3d crownPos = targetEntity.getPos();
        String posText = "x: " + Math.round(crownPos.x) + " y: " + Math.round(crownPos.y) + " z: " + Math.round(crownPos.z);
        return "§3" + posText;
    }

    //TODO Fix this with new implementation
    public static String getTargetName() {
        String nameText = targetEntity.getEntityName();
        return "§6" + nameText;
    }

    //TODO Fix this with new implementation
    public static String getDivider() {
        return "§8 | ";
    }

    public static void setTargetEntity(LivingEntity entity) {
        targetEntity = entity;
    }

    public static void removeTargetEntity() {
        targetEntity = null;
    }
}
