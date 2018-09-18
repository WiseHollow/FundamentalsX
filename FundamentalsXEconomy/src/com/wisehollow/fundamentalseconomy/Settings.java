package com.wisehollow.fundamentalseconomy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class Settings implements Listener {

    public static Long startingBalance;
    public static Long maximumBalance;
    public static Long minimumBalance;
    public static Double minimumPaid;
    public static Character currencySymbol;
    public static String currencySingular;
    public static String currencyPlural;

    private void load() {
        Main.getPlugin().getLogger().info("Loading FundamentalsXEconomy configuration.");
        FileConfiguration configuration = com.wisehollow.fundamentals.Main.getPlugin().getConfig();

        startingBalance = configuration.getLong("Starting_Balance");
        maximumBalance = configuration.getLong("Max_Balance");
        minimumBalance = configuration.getLong("Min_Balance");
        minimumPaid = configuration.getDouble("Min_Pay_Amount");
        currencySymbol = configuration.getString("Currency_Symbol").charAt(0);
        currencySingular = configuration.getString("Currency_Singular");
        currencyPlural = configuration.getString("Currency_Plural");

    }

    @EventHandler
    public void onFundamentalsXLoad(PluginEnableEvent event) {
        if (event.getPlugin().getName().equalsIgnoreCase("FundamentalsX")) {
            load();
        }
    }

}
