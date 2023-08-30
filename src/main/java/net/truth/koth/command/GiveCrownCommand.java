package net.truth.koth.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.truth.koth.KingOfTheHill;
import net.truth.koth.item.ModItems;
import net.truth.koth.item.custom.CrownLocatorItem;
import net.truth.koth.item.custom.KingsCrownItem;
import net.truth.koth.util.CrownUtil;

import static net.minecraft.command.argument.EntityArgumentType.getPlayer;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveCrownCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal(KingOfTheHill.MOD_ID)
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("giveCrown")
                        .then(argument("player", EntityArgumentType.player())
                                .executes((command) -> {
                                    ServerPlayerEntity player = getPlayer(command, "player");
                                    KingsCrownItem.equipItem(player, ModItems.KINGS_CROWN.getDefaultStack());
                                    return 1;
                                }))));
    }
}
