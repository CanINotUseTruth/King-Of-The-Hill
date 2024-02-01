package net.truth.koth;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.truth.koth.command.ClearCrownCommand;
import net.truth.koth.command.GiveCrownCommand;
import net.truth.koth.config.Config;
import net.truth.koth.event.DeathEvent;
import net.truth.koth.item.ModItemGroup;
import net.truth.koth.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KingOfTheHill implements ModInitializer {
	public static final String MOD_ID = "koth";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Config CONFIG = Config.load();

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroup.registerItemGroups();
		loadEvents();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			ClearCrownCommand.register(dispatcher);
			GiveCrownCommand.register(dispatcher);
		});
	}

	private void loadEvents() {
		ServerLivingEntityEvents.ALLOW_DEATH.register((LivingEntity livingEntity, DamageSource damageSource, float damageAmount) -> {
			if (livingEntity instanceof ServerPlayerEntity) {
				DeathEvent.onDeathEvent((ServerPlayerEntity)livingEntity, damageSource);
			}
			return true;
		});
	}
}
