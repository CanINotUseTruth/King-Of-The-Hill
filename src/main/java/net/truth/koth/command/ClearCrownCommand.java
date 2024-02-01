package net.truth.koth.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.truth.koth.KingOfTheHill;
import net.truth.koth.item.ModItems;
import net.truth.koth.util.CrownUtil;

import static net.minecraft.command.argument.EntityArgumentType.getPlayer;
import static net.minecraft.server.command.CommandManager.*;

public class ClearCrownCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal(KingOfTheHill.MOD_ID)
            .requires(source -> source.hasPermissionLevel(2))
            .then(literal("clearCrown")
                .then(argument("player", EntityArgumentType.player())
                    .executes((command) -> {
                        ServerPlayerEntity player = getPlayer(command, "player");
                        var optional = TrinketsApi.getTrinketComponent(player);
                        // Check Inventory slots
                        if(player.getInventory().contains(ModItems.KINGS_CROWN.getDefaultStack())) {
                            for(int i = 0; i < player.getInventory().size() - 1; i++) {
                                if(player.getInventory().getStack(i).getItem() == ModItems.KINGS_CROWN) {
                                    player.getInventory().setStack(i, Items.AIR.getDefaultStack());
                                    KingOfTheHill.LOGGER.info("Crown trinket removed from player");
                                }
                            }
                        }
                        // Check trinket slot
                        if ((optional.isPresent()) && !player.getWorld().isClient()) {
                            TrinketComponent comp = optional.get();
                            if (CrownUtil.hasCrown(comp)) {
                                CrownUtil.deleteCrown(comp);
                                KingOfTheHill.LOGGER.info("Crown trinket removed from player");
                            }
                        }
                        return 1;
                    }))));
    }
}
