package com.wisehollow.fundamentalseconomy.tasks;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentalseconomy.data.EconomyProfile;
import com.wisehollow.fundamentalseconomy.utils.EconomyProfileComparator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class BalanceTopTask extends Thread {

    private final CommandSender commandSender;
    private final List<EconomyProfile> balTop;

    public BalanceTopTask(CommandSender commandSender) {
        this.commandSender = commandSender;
        this.balTop = new ArrayList<>(11);
    }

    @Override
    public void run() {
        File economyDataDirectory = new File("plugins" + File.separator + com.wisehollow.fundamentals.Main.getPlugin().getDescription().getName() +
                File.separator + "Economy Data");
        if (economyDataDirectory.isDirectory()) {
            for (File file : Objects.requireNonNull(economyDataDirectory.listFiles(pathname -> pathname.getName().toLowerCase().endsWith(".econ")))) {
                EconomyProfile loadedProfile = load(file);
                if (loadedProfile != null) {
                    add(loadedProfile);
                }
            }
        }

        report();
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

    private void add(EconomyProfile loadedProfile) {
        if (loadedProfile != null) {
            balTop.add(loadedProfile);
            sort();
            if (balTop.size() > 10) {
                balTop.remove(10);
            }
        }
    }

    private void sort() {
        balTop.sort(new EconomyProfileComparator());
    }

    private void report() {
        commandSender.sendMessage(Language.getInstance().prefixInfo + " --- FundamentalsX Economy --- ");
        balTop.forEach(economyProfile -> commandSender.sendMessage(Language.getInstance().prefixInfo + economyProfile.getPlayerName() + " - " + economyProfile.getBalance()));
    }
}
