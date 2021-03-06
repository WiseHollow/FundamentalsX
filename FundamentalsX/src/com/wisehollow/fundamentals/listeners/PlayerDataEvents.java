package com.wisehollow.fundamentals.listeners;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class PlayerDataEvents implements Listener {

    @EventHandler
    public void InitializePlayerDataOnJoin(PlayerJoinEvent event) {
        if (PlayerData.getPlayerData(event.getPlayer()) == null) {
            PlayerData.LoadPlayerData(event.getPlayer());
        }
    }

    @EventHandler
    public void unloadAndSaveOnQuit(PlayerQuitEvent event) {
        PlayerData playerData = PlayerData.getPlayerData(event.getPlayer());
        if (playerData != null) {
            playerData.setLastLocation(event.getPlayer().getLocation().clone());
            playerData.save();
            PlayerData.UnloadPlayerData(playerData);
        }
    }

}
