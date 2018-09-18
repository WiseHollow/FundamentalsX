package com.wisehollow.fundamentals.listeners;

import com.wisehollow.fundamentals.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.stream.Collectors;

public class PlayerMessageEvents implements Listener {

    @EventHandler
    public void LoginMessage(PlayerJoinEvent event) {
        event.setJoinMessage(Settings.JoinMessage.replace("%p", event.getPlayer().getName()));
    }

    @EventHandler
    public void QuitMessage(PlayerQuitEvent event) {
        event.setQuitMessage(Settings.QuitMessage.replace("%p", event.getPlayer().getName()));
    }

    @EventHandler
    public void motdDisplay(PlayerJoinEvent event) {
        Integer online = Bukkit.getOnlinePlayers().stream()
                .filter(player -> (!player.hasMetadata("vanished") || player.getMetadata("vanished").equals(false)))
                .collect(Collectors.toList()).size();

        String motd = Settings.motd;
        motd = motd.replace("%p", event.getPlayer().getName());
        motd = motd.replace("%c", online.toString());
        motd = ChatColor.translateAlternateColorCodes('&', motd);
        motd += "\n ";
        event.getPlayer().sendMessage(motd);
    }

}
