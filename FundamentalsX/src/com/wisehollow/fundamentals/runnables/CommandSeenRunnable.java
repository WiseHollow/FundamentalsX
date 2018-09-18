package com.wisehollow.fundamentals.runnables;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class CommandSeenRunnable implements Runnable {

    private final CommandSender commandSender;
    private final String username;

    public CommandSeenRunnable(final CommandSender commandSender, final String username) {
        this.commandSender = commandSender;
        this.username = username;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        if (Bukkit.getOnlineMode()) {
            Optional<String> offlinePlayerUUID = PlayerUtil.getOfflinePlayerUUID(username);
            if (offlinePlayerUUID.isPresent()) {
                final String playerUUID = offlinePlayerUUID.get();
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
                if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
                    final Optional<PlayerData> optionalPlayerData = PlayerData.LoadPlayerData(offlinePlayer);
                    optionalPlayerData.ifPresent(playerData -> sendDetails(playerData, offlinePlayer.getLastPlayed()));
                }
            }
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
            if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
                final Optional<PlayerData> optionalPlayerData = PlayerData.LoadPlayerData(offlinePlayer);
                optionalPlayerData.ifPresent(playerData -> sendDetails(playerData, offlinePlayer.getLastPlayed()));
            }
        }
    }

    private void sendDetails(PlayerData playerData, long lastPlayed) {
        final Location targetLocation = playerData.getLastLocation();
        commandSender.sendMessage(Language.PREFIX + "Player information: " + ChatColor.RESET + playerData.getName());
        commandSender.sendMessage(Language.PREFIX + "Last logged in: " + ChatColor.RESET + convertTime(lastPlayed));
        commandSender.sendMessage(Language.PREFIX + "Location: " + ChatColor.RESET
                + targetLocation.getWorld().getName() + "   " + targetLocation.getBlockX()
                + " " + targetLocation.getBlockY() + " " + targetLocation.getBlockZ());
    }

    private String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
