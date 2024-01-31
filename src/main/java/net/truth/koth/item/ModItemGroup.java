package net.truth.koth.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.truth.koth.KingOfTheHill;

public class ModItemGroup {
    public static final ItemGroup KOTH = FabricItemGroupBuilder.build(
            new Identifier(KingOfTheHill.MOD_ID, "creative_tab"), () -> new ItemStack(ModItems.KINGS_CROWN));
}
