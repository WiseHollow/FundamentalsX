package com.wisehollow.fundamentalschat.tasks;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by John on 10/20/2016.
 */
public class MuteTask implements Listener {
    private static List<MuteTask> tasks = new ArrayList<>();

    public static Optional<MuteTask> getTask(Player player) {
        return tasks.stream().filter(task -> task.player.equals(player)).findFirst();
    }

    public static Optional<MuteTask> getOfflineTask(String name) {
        Optional<MuteTask> optionalMuteTask = tasks.stream().filter(task -> task.name.equalsIgnoreCase(name)).findFirst();
        if (!optionalMuteTask.isPresent())
            return tasks.stream().filter(task -> task.name.contains(name)).findFirst();
        else
            return optionalMuteTask;
    }

    public static MuteTask insertTask(Player player) {
        if (!getOfflineTask(player.getName()).isPresent()) {
            MuteTask muteTask = new MuteTask(player);
            tasks.add(muteTask);
            return muteTask;
        } else {
            return getOfflineTask(player.getName()).get();
        }
    }

    private OfflinePlayer player;
    private UUID uuid;
    private String name;

    private MuteTask(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public void Run() {
        if (player.isOnline())
            ((Player) player).sendMessage(Language.getInstance().muted);
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
    }

    public void Disable() {
        if (player.isOnline())
            ((Player) player).sendMessage(Language.getInstance().unmuted);
        tasks.remove(this);
        unregisterAll();
    }

    private void unregisterAll() {
        AsyncPlayerChatEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
        PlayerJoinEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void preventChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().getUniqueId().equals(uuid)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Language.getInstance().muted);
        }
    }

    @EventHandler
    public void unregisterOnQuit(PlayerQuitEvent event) {
        if (event.getPlayer().getUniqueId().equals(uuid)) {
            unregisterAll();
        }
    }

    @EventHandler
    public void registerOnJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getUniqueId().equals(uuid)) {
            unregisterAll();
            Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
        }
    }
}
