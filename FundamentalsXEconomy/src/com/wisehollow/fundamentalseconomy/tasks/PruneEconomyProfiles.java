package com.wisehollow.fundamentalseconomy.tasks;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentalseconomy.Settings;
import com.wisehollow.fundamentalseconomy.data.EconomyProfile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

public class PruneEconomyProfiles extends Thread {

    private CommandSender commandSender;

    public PruneEconomyProfiles(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public void run() {
        File economyDataDirectory = new File("plugins" + File.separator + com.wisehollow.fundamentals.Main.getPlugin().getDescription().getName() +
                File.separator + "Economy Data");
        if (economyDataDirectory.isDirectory()) {
            for (File file : Objects.requireNonNull(economyDataDirectory.listFiles(pathname -> pathname.getName().toLowerCase().endsWith(".econ")))) {
                EconomyProfile loadedProfile = load(file);
                if (loadedProfile != null
                        && (loadedProfile.getPlayerName() == null || Bukkit.getPlayer(loadedProfile.getPlayerName()) == null)
                        && loadedProfile.getBalance() == Settings.startingBalance) {
                    try {
                        Files.delete(file.toPath());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }

        commandSender.sendMessage(Language.getInstance().prefixInfo + "Finished pruning Economy Profiles!");
    }

    private EconomyProfile load(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        UUID uuid;
        try {
            uuid = UUID.fromString(file.getName().substring(0, file.getName().indexOf(".econ")));
        } catch (IllegalArgumentException exception) {
            return null;
        }
        if (config.isDouble("balance")) {
            double balance = config.getDouble("balance");
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            return new EconomyProfile(offlinePlayer, balance);
        } else {
            return null;
        }
    }
}
