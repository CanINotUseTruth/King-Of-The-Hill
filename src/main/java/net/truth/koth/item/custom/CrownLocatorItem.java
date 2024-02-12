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
import net.truth.koth.KingOfTheHill;

public class CrownLocatorItem extends Item {

    private static LivingEntity targetEntity = null;
    public CrownLocatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            if(targetEntity != null) {
                if(!world.getServer().getPlayerManager().getPlayerList().contains(targetEntity)){
                    user.sendMessage(Text.of("§6The King is Offline!"), true);
                    user.getItemCooldownManager().set(this, 120);
                    return TypedActionResult.success(this.getDefaultStack());
                }

                if(targetEntity.getWorld().getDimension() != user.getWorld().getDimension()) {
                    user.sendMessage(Text.of("§6The King is in a Different Dimension" + getDivider() + getDimensionName()), true);
                    user.getItemCooldownManager().set(this, 120);
                    return TypedActionResult.success(this.getDefaultStack());
                }

                if(targetEntity.getUuid() == user.getUuid()) {
                    user.sendMessage(Text.of("§6You're the King"), true);
                    user.getItemCooldownManager().set(this, 120);
                    return TypedActionResult.success(this.getDefaultStack());
                }

                if(KingOfTheHill.CONFIG.EXACT_COORDINATES) {
                    user.sendMessage(Text.of(getTargetName() + getDivider() + getCrownPos() + getDivider() + getDimensionName()), true);
                } else {
                    user.sendMessage(Text.of(getTargetName() + getDivider() + getCrownDirection(user) + getDivider() + getDimensionName()), true);
                }
            } else {
                user.sendMessage(Text.of("§6No King Found!"), true);
            }
        }
        user.getItemCooldownManager().set(this, 120);
        return TypedActionResult.success(this.getDefaultStack());
    }

    public static String getDimensionName() {
        String dimensionKey = targetEntity.getWorld().getRegistryKey().getValue().getNamespace();
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

    public static String getCrownPos() {
        Vec3d crownPos = targetEntity.getPos();
        String posText = "x: " + Math.round(crownPos.x) + " y: " + Math.round(crownPos.y) + " z: " + Math.round(crownPos.z);
        return "§3" + posText;
    }

    public static String getCrownDirection(PlayerEntity user) {

        double xDiff = targetEntity.getX() - user.getX();
        double yDiff = targetEntity.getY() - user.getY();
        double zDiff = targetEntity.getZ() - user.getZ();
        double finalDist = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff) + (zDiff * zDiff));
        String directionText = "";

        double angle = Math.toDegrees(Math.atan2(zDiff, xDiff));
        if (angle < 0) {
            angle += 360;
        }

        if (angle >= 22.5 && angle < 67.5) {
            directionText = "SE";
        } else if (angle >= 67.5 && angle < 112.5) {
            directionText = "S";
        } else if (angle >= 112.5 && angle < 157.5) {
            directionText = "SW";
        } else if (angle >= 157.5 && angle < 202.5) {
            directionText = "W";
        } else if (angle >= 202.5 && angle < 247.5) {
            directionText = "NW";
        } else if (angle >= 247.5 && angle < 292.5) {
            directionText = "N";
        } else if (angle >= 292.5 && angle < 337.5) {
            directionText = "NE";
        } else {
            directionText = "E";
        }

        if (xDiff < KingOfTheHill.CONFIG.VERTICAL_RADIUS || zDiff < KingOfTheHill.CONFIG.VERTICAL_RADIUS) {
            if(yDiff > 0) {
                directionText += " U";
            } else if (yDiff < 0) {
                directionText += " D";
            } else {
                directionText += " -";
            }
        }

        return "§3" + Math.round(finalDist) + " Blocks " + directionText;
    }

    public static String getTargetName() {
        String nameText = targetEntity.getEntityName();
        return "§6" + nameText;
    }

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
