package com.wisehollow.fundamentalscombat.events;

import com.wisehollow.fundamentalscombat.Settings;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LegacyCombatEvents implements Listener {

    private final double noCoolDownSpeed = 16d;
    private final double standardCoolDownSpeed = 4d;

    private void applyAttackCoolDown(Player player) {
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)
                .setBaseValue(Settings.enableLegacyCombat ? noCoolDownSpeed : standardCoolDownSpeed);
    }

    @EventHandler
    public void legacyCombatOnJoin(PlayerJoinEvent event) {
        applyAttackCoolDown(event.getPlayer());
    }

    @EventHandler
    public void legacyCombatOnQuit(PlayerQuitEvent event) {
        applyAttackCoolDown(event.getPlayer());
    }

}
