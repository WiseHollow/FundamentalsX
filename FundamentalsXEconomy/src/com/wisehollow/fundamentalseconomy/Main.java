package com.wisehollow.fundamentalseconomy;

import com.wisehollow.fundamentalseconomy.commands.CommandBalance;
import com.wisehollow.fundamentalseconomy.commands.CommandBalanceTop;
import com.wisehollow.fundamentalseconomy.commands.CommandEcon;
import com.wisehollow.fundamentalseconomy.commands.CommandPay;
import com.wisehollow.fundamentalseconomy.events.EconomyEvents;
import com.wisehollow.fundamentalseconomy.vault.FXEconomy;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;
    private static Vault vault;
    private static Permission permissions;

    public static Main getPlugin() {
        return plugin;
    }

    public static Permission getPermissions() {
        return permissions;
    }

    private Settings settings;

    @Override
    public void onEnable() {
        plugin = this;
        settings = new Settings();
        registerCommands();
        getServer().getPluginManager().registerEvents(settings, this);
        getServer().getPluginManager().registerEvents(new EconomyEvents(), this);
        getLogger().info("Registering economy with Vault: " + (registerEconomyService() ? "successful" : "failed"));
        getLogger().info("Hooking into Vault Permissions: " + (setupPermissions() ? "successful" : "failed"));
        getLogger().info(plugin.getName() + " is now enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(plugin.getName() + " is now disabled!");
    }

    private boolean registerEconomyService() {
        vault = (Vault) Bukkit.getPluginManager().getPlugin("Vault");
        FXEconomy fxEconomy = new FXEconomy();
        Bukkit.getServicesManager().register(Economy.class, fxEconomy, vault, ServicePriority.Normal);
        return vault != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permissions = rsp.getProvider();
        return permissions != null;
    }

    private void registerCommands() {
        CommandBalance commandBalance = new CommandBalance();
        getCommand("bal").setExecutor(commandBalance);
        getCommand("balance").setExecutor(commandBalance);
        getCommand("baltop").setExecutor(new CommandBalanceTop());
        getCommand("pay").setExecutor(new CommandPay());
        getCommand("econ").setExecutor(new CommandEcon());
    }

}
