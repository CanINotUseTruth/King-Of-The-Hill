package net.truth.koth.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.truth.koth.KingOfTheHill;

public class ModItemGroup {
    public static final ItemGroup FOODABLES_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(KingOfTheHill.MOD_ID, "creative_tab"),

            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.koth")).icon(() -> new ItemStack(ModItems.KINGS_CROWN)).entries((displayContext, entries) -> {
                entries.add(ModItems.KINGS_CROWN);
                entries.add(ModItems.CROWN_LOCATOR);
            }).build());
    public static void registerItemGroups() {
    }
}
