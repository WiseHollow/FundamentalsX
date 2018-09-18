package com.wisehollow.fundamentalsantigrief.Events;

import com.wisehollow.fundamentalsantigrief.Settings;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

/**
 * Created by John on 10/22/2016.
 */
public class LivingEntityGriefEvents implements Listener {
    @EventHandler
    public void PreventEndermanGrief(EntityChangeBlockEvent event) {
        if (event.isCancelled() || !Settings.PreventEndermanGrief || !(event.getEntity() instanceof Enderman))
            return;

        event.setCancelled(true);
    }
}
