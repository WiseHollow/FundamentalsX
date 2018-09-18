package com.wisehollow.fundamentals.listeners;

import com.wisehollow.fundamentals.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvents implements Listener {

    @EventHandler
    public void preventLavaAndFireDamage(EntityDamageEvent event) {
        if (!event.isCancelled()) {
            if (event.getCause() == EntityDamageEvent.DamageCause.LAVA && Settings.PreventLavaDamage) {
                event.setCancelled(true);
                event.setDamage(0);
            } else if (event.getCause() == EntityDamageEvent.DamageCause.FIRE && Settings.PreventFireDamage) {
                event.setCancelled(true);
                event.setDamage(0);
            }
        }
    }

}
