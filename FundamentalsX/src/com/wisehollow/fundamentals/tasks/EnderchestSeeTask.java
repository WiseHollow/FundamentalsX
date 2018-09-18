package com.wisehollow.fundamentals.tasks;

import com.wisehollow.fundamentals.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Created by John on 10/19/2016.
 */
public class EnderchestSeeTask implements CustomTask, Listener {
    private Player viewer;
    private Player target;
    private boolean editable;

    public EnderchestSeeTask(Player viewer, Player target) {
        this.viewer = viewer;
        this.target = target;
        this.editable = viewer.equals(target) || viewer.hasPermission("Fundamentals.Enderchest.Other.Edit");
    }

    @Override
    public boolean run() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getPlugin());
        if (viewer != null && target != null) {
            viewer.openInventory(target.getEnderChest());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void disable() {
        InventoryClickEvent.getHandlerList().unregister(this);
        InventoryDragEvent.getHandlerList().unregister(this);
        InventoryCloseEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void preventClick(InventoryClickEvent event) {
        if (!event.isCancelled()) {
            if (!editable)
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void preventDrag(InventoryDragEvent event) {
        if (!event.isCancelled()) {
            if (!editable)
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent event) {
        if (event.getPlayer().equals(viewer)) {
            disable();
        }
    }
}
