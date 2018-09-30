package com.wisehollow.fundamentals.tasks;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.permissions.PermissionCheck;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

/**
 * Created by John on 10/13/2016.
 */
public class TeleportTask implements CustomTask, Listener {
    public static HashMap<Entity, Location> PreviousLocation = new HashMap<>(); //TODO: Make listen to teleport event and update on ANY teleport on server.

    private static void teleport(Entity entity, Location target) {
        entity.sendMessage(Language.getInstance().teleporting);
        entity.teleport(target);
    }

    private Entity entity;
    private Location target;
    private int seconds;

    private Location initialLocation;
    private int taskID = -1;

    public TeleportTask(Entity e, Location t, int s) {
        entity = e;
        target = t;
        seconds = s;
    }

    @Override
    public boolean run() {
        if (PermissionCheck.HasAdminPermissions(entity)) {
            teleport(entity, target);
            return true;
        }

        initialLocation = entity.getLocation().clone();
        Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
        entity.sendMessage(Language.getInstance().teleportingWarmUp
                .replace("%s", Integer.toString(seconds)));
        taskID = Main.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
        {
            disable();
            if (entity != null)
                teleport(entity, target);
        }, 20 * seconds);
        return true;
    }

    @Override
    public void disable() {
        PlayerMoveEvent.getHandlerList().unregister(this);
        taskID = -1;
    }

    @EventHandler
    public void OnMove(PlayerMoveEvent event) {
        if (!event.getPlayer().equals(entity))
            return;

        double distance = initialLocation.distance(event.getPlayer().getLocation());
        if (distance > 2) {
            Main.getPlugin().getServer().getScheduler().cancelTask(taskID);
            PlayerMoveEvent.getHandlerList().unregister(this);
            entity.sendMessage(Language.getInstance().teleportingCancelled);
        }
    }
}
