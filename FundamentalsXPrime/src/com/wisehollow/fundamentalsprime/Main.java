package com.wisehollow.fundamentalsprime;

import com.wisehollow.fundamentalsprime.commands.CommandHat;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin = null;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        registerCommands();

        getLogger().info(getName() + " is now enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(getName() + " is now disabled!");
    }

    private void registerCommands() {
        this.getCommand("Hat").setExecutor(new CommandHat());
    }

}
