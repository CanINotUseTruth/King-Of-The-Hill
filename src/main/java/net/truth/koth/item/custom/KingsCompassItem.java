package net.truth.koth.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.truth.koth.KingOfTheHill;

public class KingsCompassItem extends Item {

    private static LivingEntity targetEntity = null;
    public KingsCompassItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            CommandManager commandManager = world.getServer().getCommandManager();
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

        switch(dimensionKey){
            case "the_nether":
                dimensionText = "The Nether";
                dimensionColor = "dark_red";
                break;
            case "overworld":
                dimensionText = "Overworld";
                dimensionColor = "dark_green";
                break;
            case "the_end":
                dimensionText = "The End";
                dimensionColor = "dark_purple";
                break;
            default:
                dimensionText = dimensionKey;
                dimensionColor = "white";
        }
        String finalText = "{\"text\":\"" + dimensionText + "\",\"color\":\"" + dimensionColor +"\"}";
        return finalText;
    }

    public static String getCrownPosJSONObj() {
        Vec3d crownPos = targetEntity.getPos();
        String posText = "x: " + Math.round(crownPos.x) + " y: " + Math.round(crownPos.y) + " z: " + Math.round(crownPos.z);
        String finalText = "{\"text\":\"" + posText + " \",\"color\":\"aqua\"}";
        return finalText;
    }

    public static String getTargetJSONObj() {
        String nameText = targetEntity.getEntityName();
        String finalText = "{\"text\":\"" + nameText + " \",\"color\":\"gold\"}";
        return finalText;
    }

    public static void setTargetEntity(LivingEntity entity) {
        targetEntity = entity;
    }

    public static void removeTargetEntity() {
        targetEntity = null;
    }
}
