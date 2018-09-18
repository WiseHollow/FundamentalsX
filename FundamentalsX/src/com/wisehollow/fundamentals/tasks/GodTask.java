package com.wisehollow.fundamentals.tasks;


import com.wisehollow.fundamentals.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/13/2016.
 */
public class GodTask implements CustomTask, Listener {
    private static List<GodTask> tasks = new ArrayList<>();

    public static GodTask GetTask(Player player) {
        for (GodTask t : tasks) {
            if (t.player.equals(player))
                return t;
        }

        return null;
    }

    private Player player;

    public GodTask(Player p) {
        super();
        player = p;
    }

    @Override
    public boolean run() {
        tasks.add(this);
        Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
        return true;
    }

    @Override
    public void disable() {
        PlayerQuitEvent.getHandlerList().unregister(this);
        EntityDamageEvent.getHandlerList().unregister(this);
        EntityTargetLivingEntityEvent.getHandlerList().unregister(this);
        tasks.remove(this);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(Main.getPlugin(), player);
        }
    }

    @EventHandler
    public void TargetCheck(EntityTargetLivingEntityEvent event) {
        if (event.isCancelled() || event.getTarget() == null || !event.getTarget().equals(player))
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
