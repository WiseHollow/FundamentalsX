package com.wisehollow.fundamentalsantigrief.Events;

import com.wisehollow.fundamentalsantigrief.Settings;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

/**
 * Created by John on 10/22/2016.
 */
public class FireEvents implements Listener {

    @EventHandler
    public void PreventFireSpread(BlockIgniteEvent event) {
        if (event.isCancelled() || event.getCause() != BlockIgniteEvent.IgniteCause.SPREAD || !Settings.PreventFireSpread)
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void PreventFireSpread(BlockBurnEvent event) {
        if (event.isCancelled() || !Settings.PreventFireSpread)
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void PreventFireSpread(BlockFromToEvent event) {
        if (event.isCancelled() || !Settings.PreventFireSpread || event.getBlock().getType() != Material.FIRE)
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void PreventFireSpread(BlockSpreadEvent event) {
        if (event.isCancelled() || !Settings.PreventFireSpread || event.getSource().getType() != Material.FIRE)
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void PreventFireCreation(BlockIgniteEvent event) {
        if (event.getIgnitingEntity().hasPermission("Fundamentals.AntiGrief.Bypass"))
            return;
        if (event.isCancelled() || event.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL || !Settings.PreventFireCreation)
            return;
        event.setCancelled(true);
    }
}
