package com.wisehollow.fundamentals.listeners;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.stream.Collectors;

public class PlayerMessageEvents implements Listener {

    @EventHandler
    public void firstTimeJoinMessage(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            String welcomeMessage = Language.getInstance().welcome.replace("%p", event.getPlayer().getName());
            Bukkit.getServer().getOnlinePlayers().stream()
                    .filter(player -> player.hasPermission("Fundamentals.Welcome") && !player.equals(event.getPlayer()))
                    .forEach(player -> {
                        player.sendMessage(welcomeMessage);
                    });
        }
    }

    @EventHandler
    public void loginMessage(PlayerJoinEvent event) {
        event.setJoinMessage(Settings.JoinMessage.replace("%p", event.getPlayer().getName()));
    }

    @EventHandler
    public void quitMessage(PlayerQuitEvent event) {
        event.setQuitMessage(Settings.QuitMessage.replace("%p", event.getPlayer().getName()));
    }

    @EventHandler
    public void motdDisplay(PlayerJoinEvent event) {
        int online = Bukkit.getOnlinePlayers().stream()
                .filter(player -> (!player.hasMetadata("vanished") || player.getMetadata("vanished").equals(false)))
                .collect(Collectors.toList()).size();

        String motd = Settings.motd;
        motd = motd.replace("%p", event.getPlayer().getName());
        motd = motd.replace("%c", Integer.toString(online));
        motd = ChatColor.translateAlternateColorCodes('&', motd);
        motd += "\n ";
        event.getPlayer().sendMessage(motd);
    }

}
