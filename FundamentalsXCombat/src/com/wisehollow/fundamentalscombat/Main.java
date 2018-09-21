package com.wisehollow.fundamentalscombat;

import com.wisehollow.fundamentalscombat.commands.CommandPVP;
import com.wisehollow.fundamentalscombat.events.CombatLoggingEvents;
import com.wisehollow.fundamentalscombat.events.HeadDropEvents;
import com.wisehollow.fundamentalscombat.events.LegacyCombatEvents;
import com.wisehollow.fundamentalscombat.events.PVPTimerEvents;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;
    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        getLogger().info(plugin.getName() + " is now disabled!");
    }

    @Override
    public void onEnable() {
        plugin = this;
        Settings.load();
        registerEvents();
        getCommand("PVP").setExecutor(new CommandPVP());
        getLogger().info(plugin.getName() + " is now enabled!");
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new LegacyCombatEvents(), this);
        if (Settings.preventCombatLogging)
            getServer().getPluginManager().registerEvents(new CombatLoggingEvents(), this);
        if (Settings.playersDropHead)
            getServer().getPluginManager().registerEvents(new HeadDropEvents(), this);
        if (Settings.delayedPVPForNewPlayers)
            getServer().getPluginManager().registerEvents(new PVPTimerEvents(), this);
    }

    @Override
    public FileConfiguration getConfig() {
        return com.wisehollow.fundamentals.Main.getPlugin().getConfig();
    }

}
