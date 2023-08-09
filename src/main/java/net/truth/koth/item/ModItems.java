package net.truth.koth.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.truth.koth.KingOfTheHill;
import net.truth.koth.item.custom.CrownLocatorItem;
import net.truth.koth.item.custom.KingsCrownItem;

public class ModItems {
    public static final Item KINGS_CROWN = registerItem("kings_crown",
            new KingsCrownItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.KOTH)));
    public static final Item CROWN_LOCATOR = registerItem("crown_locator",
            new CrownLocatorItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.KOTH)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(KingOfTheHill.MOD_ID, name), item);
    }

    public static void registerModItems() {
        KingOfTheHill.LOGGER.debug("Registering Mod Items for " + KingOfTheHill.MOD_ID);
    }
}
