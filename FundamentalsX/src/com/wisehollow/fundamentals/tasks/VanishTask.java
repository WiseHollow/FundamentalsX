package com.wisehollow.fundamentals.tasks;


import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.permissions.PermissionCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/13/2016.
 */
public class VanishTask implements CustomTask, Listener {
    private static List<VanishTask> tasks = new ArrayList<>();

    public static VanishTask GetTask(Player player) {
        for (VanishTask t : tasks) {
            if (t.player.equals(player))
                return t;
        }

        return null;
    }

    private Player player;

    public VanishTask(Player p) {
        player = p;
    }

    @Override
    public boolean run() {
        tasks.add(this);
        Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
        Update();
        return true;
    }

    @Override
    public void disable() {
        PlayerJoinEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
        EntityTargetLivingEntityEvent.getHandlerList().unregister(this);
        EntityDamageEvent.getHandlerList().unregister(this);
        tasks.remove(this);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(Main.getPlugin(), player);
        }
    }

    private void Update() {
        if (player == null || !player.isOnline())
            return;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!PermissionCheck.HasModeratorPermissions(p))
                p.hidePlayer(Main.getPlugin(), player);
            else
                p.showPlayer(Main.getPlugin(), player);
        }
    }

    @EventHandler
    public void LoginCheck(PlayerJoinEvent event) {
        Update();
    }

    @EventHandler
    public void TargetCheck(EntityTargetLivingEntityEvent event) {
        if (event.isCancelled() || !event.getTarget().equals(player))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void PreventHurt(EntityDamageEvent event) {
        if (event.isCancelled() || !event.getEntity().equals(player))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void RemoveOnLogout(PlayerQuitEvent event) {
        if (!event.getPlayer().equals(player))
            return;

        disable();
    }
}
