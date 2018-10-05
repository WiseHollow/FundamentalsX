package com.wisehollow.fundamentalseconomy.data;

import com.wisehollow.fundamentalseconomy.Main;
import com.wisehollow.fundamentalseconomy.Settings;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class EconomyProfile {

    private static HashMap<UUID, EconomyProfile> economyProfileHashMap = new HashMap<>();
    private static final String fileExtension = "econ";

    public static boolean exists(OfflinePlayer player) {
        return economyProfileHashMap.containsKey(player.getUniqueId());
    }

    public static Optional<EconomyProfile> get(OfflinePlayer player) {
        if (economyProfileHashMap.containsKey(player.getUniqueId())) {
            return Optional.of(economyProfileHashMap.get(player.getUniqueId()));
        } else {
            return Optional.empty();
        }
    }

    public static void unload(OfflinePlayer player) {
        if (economyProfileHashMap.containsKey(player.getUniqueId())) {
            economyProfileHashMap.get(player.getUniqueId()).save();
            economyProfileHashMap.remove(player.getUniqueId());
        }
    }

    public static EconomyProfile load(OfflinePlayer player) {
        EconomyProfile profile;
        File file = new File("plugins" + File.separator + com.wisehollow.fundamentals.Main.getPlugin().getDescription().getName() +
                File.separator + "Economy Data" + File.separator + getFileName(player));
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            double balance = config.getDouble("balance");
            profile = new EconomyProfile(player, balance);
        } else {
            profile = new EconomyProfile(player);
            profile.save();
        }

        economyProfileHashMap.put(player.getUniqueId(), profile);
        return profile;
    }

    public static EconomyProfile load(String playerName) {
        return null;
    }

    private static String getFileName(OfflinePlayer player) {
        return player.getUniqueId().toString() + "." + fileExtension;
    }

    private OfflinePlayer player;
    private double balance;

    private EconomyProfile(OfflinePlayer player) {
        this.player = player;
        this.balance = Settings.startingBalance;
    }

    public EconomyProfile(OfflinePlayer player, double balance) {
        this.player = player;
        this.balance = balance;
    }

    public String getPlayerName() {
        return player.getName();
    }
    public double getBalance() {
        return balance;
    }

    public EconomyProfile subtract(double amount) {
        this.balance -= amount;
        return this;
    }

    public EconomyProfile add(double amount) {
        this.balance += amount;
        return this;
    }

    public void save() {
        File directory = new File("plugins" + File.separator + com.wisehollow.fundamentals.Main.getPlugin().getDescription().getName() +
                File.separator + "Economy Data");
        File file = new File("plugins" + File.separator + com.wisehollow.fundamentals.Main.getPlugin().getDescription().getName() +
                File.separator + "Economy Data" + File.separator + getFileName(player));
        if (!directory.isDirectory()) {
            if (directory.mkdirs()) {
                Main.getPlugin().getLogger().info("Created Economy Data directory successfully!");
            } else {
                Main.getPlugin().getLogger().severe("Failure trying to create Economy Data directory!");
                return;
            }
        }

        if (!file.isFile()) {
            try {
                file.createNewFile();
                Main.getPlugin().getLogger().info("Created Economy Data file for " + player.getName() + " successfully!");
            } catch (IOException exception) {
                Main.getPlugin().getLogger().severe("Failure trying to create Economy Data file for " + player.getName() + "!");
                exception.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("balance", balance);

        try {
            config.save(file);
        } catch (IOException exception) {
            Main.getPlugin().getLogger().severe("Failure trying to save Economy Data for " + player.getName() + "!");
            exception.printStackTrace();
        }
    }

}
