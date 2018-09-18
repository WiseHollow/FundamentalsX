package com.wisehollow.fundamentalschat;

import com.wisehollow.fundamentalschat.commands.CommandMute;
import com.wisehollow.fundamentalschat.listeners.ChatEvents;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main plugin = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public static Chat getChat() {
        return chat;
    }

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        getLogger().info("Permissions hook: " + setupPermissions());
        getLogger().info("Chat hook: " + setupChat());

        this.getCommand("Mute").setExecutor(new CommandMute());

        getServer().getPluginManager().registerEvents(new Settings(), this);
        getServer().getPluginManager().registerEvents(new ChatEvents(), this);

        getLogger().info(plugin.getName() + " is now enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(plugin.getName() + " is now disabled!");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        try {
            perms = rsp.getProvider();
        } catch (Exception exception) {
        }
        return perms != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        try {
            chat = rsp.getProvider();
        } catch (Exception exception) {
        }
        return chat != null;
    }

}
