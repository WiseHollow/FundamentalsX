package com.wisehollow.fundamentalsantigrief;

import com.wisehollow.fundamentalsantigrief.Events.ExplosionEvents;
import com.wisehollow.fundamentalsantigrief.Events.FireEvents;
import com.wisehollow.fundamentalsantigrief.Events.LivingEntityGriefEvents;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        Settings.Load();

        getServer().getPluginManager().registerEvents(new ExplosionEvents(), this);
        getServer().getPluginManager().registerEvents(new FireEvents(), this);
        getServer().getPluginManager().registerEvents(new LivingEntityGriefEvents(), this);

        getLogger().info(plugin.getName() + " is now enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(plugin.getName() + " is now disabled!");
    }

    @Override
    public FileConfiguration getConfig() {
        FileConfiguration config = com.wisehollow.fundamentals.Main.getPlugin().getConfig();
        return config;
    }
}
