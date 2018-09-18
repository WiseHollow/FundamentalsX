package com.wisehollow.fundamentals.tasks;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/14/2016.
 */
public class AFKTask implements CustomTask, Listener {
    private static List<AFKTask> taskList = new ArrayList<>();

    public static AFKTask GetTask(Player player) {
        for (AFKTask t : taskList) {
            if (t.player.equals(player))
                return t;
        }

        return null;
    }

    private Player player;
    private Location location;

    public AFKTask(Player player) {
        this.player = player;
        this.location = player.getLocation().clone();
    }

    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(Language.PREFIX + PlayerUtil.GetPlayerPrefix(player) + player.getName() + ChatColor.RESET + " is now AFK.");
        }

        taskList.add(this);
        Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
        return true;
    }

    @Override
    public void disable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(Language.PREFIX + PlayerUtil.GetPlayerPrefix(player) + player.getName() + ChatColor.RESET + " is no longer AFK.");
        }

        taskList.remove(this);
        PlayerMoveEvent.getHandlerList().unregister(this);
        AsyncPlayerChatEvent.getHandlerList().unregister(this);
        PlayerCommandPreprocessEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void DisableOnMove(PlayerMoveEvent event) {
        if (event.isCancelled() || !event.getPlayer().equals(player))
            return;

        if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockY() != event.getTo().getBlockY() || event.getFrom().getBlockZ() != event.getTo().getBlockZ())
            disable();
    }

    @EventHandler
    public void DisableOnChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled() || !event.getPlayer().equals(player))
            return;

        disable();
    }

    @EventHandler
    public void DisableOnCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled() || !event.getPlayer().equals(player))
            return;

        if (event.getMessage().equalsIgnoreCase("/AFK"))
            return;

        disable();
    }

    @EventHandler
    public void RemoveOnLogout(PlayerQuitEvent event) {
        if (!event.getPlayer().equals(player))
            return;

        disable();
    }
}
