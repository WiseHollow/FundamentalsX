package com.wisehollow.fundamentalsantigrief.Events;

import com.wisehollow.fundamentalsantigrief.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

/**
 * Created by John on 10/22/2016.
 */
public class ExplosionEvents implements Listener {
    @EventHandler
    public void PreventEntityPrime(ExplosionPrimeEvent event) {
        if (!event.isCancelled() && Settings.PreventEntityExplosion) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PreventBlockDamage(BlockExplodeEvent event) {
        if (!event.isCancelled() && Settings.PreventExplosiveBlockDamage) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void preventEntityBlockDamageOnExplode(EntityExplodeEvent event) {
        if (!event.isCancelled() && Settings.PreventExplosiveBlockDamage) {
            event.blockList().clear();
        }
    }
}
