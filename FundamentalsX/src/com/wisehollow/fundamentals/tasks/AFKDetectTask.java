package com.wisehollow.fundamentals.tasks;

import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/14/2016.
 */
public class AFKDetectTask implements CustomTask, Listener {
    private static List<AFKDetectTask> taskList = new ArrayList<>();

    public static AFKDetectTask GetTask(Player player) {
        for (AFKDetectTask t : taskList) {
            if (t.player.equals(player))
                return t;
        }

        return null;
    }

    private Player player;
    private int taskID;

    public AFKDetectTask(Player player) {
        this.player = player;
        this.taskID = -1;
    }

    @Override
    public boolean run() {
        Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
        taskList.add(this);
        return true;
    }

    @Override
    public void disable() {
        PlayerMoveEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
        AsyncPlayerChatEvent.getHandlerList().unregister(this);
        taskList.remove(this);
        if (taskID != 1)
            Main.getPlugin().getServer().getScheduler().cancelTask(taskID);
    }

    @EventHandler
    public void WaitForMovement(PlayerMoveEvent event) {
        if (event.isCancelled() || !event.getPlayer().equals(player))
            return;
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())
            return;

        Refresh();
    }

    @EventHandler
    public void WaitForSpeaking(AsyncPlayerChatEvent event) {
        if (event.isCancelled() || !event.getPlayer().equals(player))
            return;

        Refresh();
    }

    @EventHandler
    public void RemoveOnLogout(PlayerQuitEvent event) {
        if (!event.getPlayer().equals(player))
            return;

        disable();
    }

    private void Refresh() {
        if (taskID != 1)
            Main.getPlugin().getServer().getScheduler().cancelTask(taskID);
        taskID = Main.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
        {
            if (player == null || !player.isOnline())
                return;

            AFKTask task = new AFKTask(player);
            task.run();
        }, 20 * 60 * Settings.AFKDelay);
    }
}
