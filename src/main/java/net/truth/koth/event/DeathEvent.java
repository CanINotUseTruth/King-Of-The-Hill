package net.truth.koth.event;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.truth.koth.util.CrownUtil;

public class DeathEvent {
    public static void onDeathEvent(ServerPlayerEntity player, DamageSource source) {
        var optional = TrinketsApi.getTrinketComponent(player);
        if (optional.isEmpty() || player.getWorld().isClient()) {
            return;
        }

        TrinketComponent comp = optional.get();
        if(!CrownUtil.hasCrown(comp)) return;

        if(source.getAttacker() instanceof PlayerEntity) {
            CrownUtil.deleteCrown(comp);
            CrownUtil.giveCrown((PlayerEntity) source.getAttacker());
        }
    }
}
