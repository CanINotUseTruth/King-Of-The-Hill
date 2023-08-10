package net.truth.koth;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.truth.koth.item.ModItems;

public class KingOfTheHillClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TrinketRendererRegistry.registerRenderer(ModItems.KINGS_CROWN, (TrinketRenderer) ModItems.KINGS_CROWN);
    }
}
