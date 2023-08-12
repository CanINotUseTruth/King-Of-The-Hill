package net.truth.koth.util;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.truth.koth.item.ModItems;
import net.truth.koth.item.custom.CrownLocatorItem;

public class CrownUtil {

    public static boolean hasCrown(TrinketComponent comp) {
        for (var group : comp.getInventory().values()) {
            for (TrinketInventory inv : group.values()) {
                for (int i = 0; i < inv.size(); i++) {
                    if(inv.getStack(i).getItem() == ModItems.KINGS_CROWN){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void deleteCrown(TrinketComponent comp) {
        for (var group : comp.getInventory().values()) {
            for (TrinketInventory inv : group.values()) {
                for (int i = 0; i < inv.size(); i++) {
                    if(inv.getStack(i).getItem() == ModItems.KINGS_CROWN){
                        inv.setStack(i, Items.AIR.getDefaultStack());
                        CrownLocatorItem.removeTargetEntity();
                    }
                }
            }
        }
    }

    public static void giveCrown(PlayerEntity player) {
        TrinketItem.equipItem(player, ModItems.KINGS_CROWN.getDefaultStack());
        CrownLocatorItem.setTargetEntity(player);
    }
}
