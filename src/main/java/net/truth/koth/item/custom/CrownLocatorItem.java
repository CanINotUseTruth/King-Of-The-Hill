package net.truth.koth.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class CrownLocatorItem extends Item {

    private static LivingEntity targetEntity = null;
    public CrownLocatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            CommandManager commandManager = Objects.requireNonNull(world.getServer()).getCommandManager();
            if(targetEntity != null) {
                commandManager.executeWithPrefix(world.getServer().getCommandSource(),
                        "title " + user.getEntityName() + " actionbar [" + getTargetJSONObj() + "," + getCrownPosJSONObj() + "," + getDimensionJSONObj() + "]");
            } else {
                commandManager.executeWithPrefix(world.getServer().getCommandSource(), "title " + user.getEntityName() + " actionbar {\"text\":\"No King Found!\",\"color\":\"gold\"}");
            }
        }
        return TypedActionResult.success(this.getDefaultStack());
    }

    public static String getDimensionJSONObj() {
        String dimensionKey = targetEntity.getWorld().getDimensionKey().getValue().toShortTranslationKey();
        String dimensionText;
        String dimensionColor;

        switch (dimensionKey) {
            case "the_nether" -> {
                dimensionText = "The Nether";
                dimensionColor = "dark_red";
            }
            case "overworld" -> {
                dimensionText = "Overworld";
                dimensionColor = "dark_green";
            }
            case "the_end" -> {
                dimensionText = "The End";
                dimensionColor = "dark_purple";
            }
            default -> {
                dimensionText = dimensionKey;
                dimensionColor = "white";
            }
        }
        return "{\"text\":\"" + dimensionText + "\",\"color\":\"" + dimensionColor +"\"}";
    }

    public static String getCrownPosJSONObj() {
        Vec3d crownPos = targetEntity.getPos();
        String posText = "x: " + Math.round(crownPos.x) + " y: " + Math.round(crownPos.y) + " z: " + Math.round(crownPos.z);
        return "{\"text\":\"" + posText + " \",\"color\":\"aqua\"}";
    }

    public static String getTargetJSONObj() {
        String nameText = targetEntity.getEntityName();
        return "{\"text\":\"" + nameText + " \",\"color\":\"gold\"}";
    }

    public static void setTargetEntity(LivingEntity entity) {
        targetEntity = entity;
    }

    public static void removeTargetEntity() {
        targetEntity = null;
    }
}
