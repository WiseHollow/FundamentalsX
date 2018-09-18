package com.wisehollow.fundamentals.listeners;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class PlayerDataEvents implements Listener {

    @EventHandler
    public void InitializePlayerDataOnJoin(PlayerJoinEvent event) {
        if (PlayerData.GetPlayerData(event.getPlayer()) == null) {
            PlayerData.LoadPlayerData(event.getPlayer());
        }
    }

    @EventHandler
    public void InitializePlayerDataOnEnable(PluginEnableEvent event) {
        if (event.getPlugin() != Main.getPlugin())
            return;

        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData.LoadPlayerData(p);
        }
    }

}
