package com.wisehollow.fundamentals;

import com.wisehollow.fundamentals.commands.*;
import com.wisehollow.fundamentals.userdata.MetricsLite;
import com.wisehollow.fundamentals.listeners.*;
import com.wisehollow.fundamentals.tasks.JailTask;
import com.wisehollow.fundamentals.tasks.LagTask;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class Main extends JavaPlugin {
    private static Main plugin = null;
    private Economy economy = null;
    private Chat chat = null;

    public static Main getPlugin() {
        return plugin;
    }

    public Economy getEconomy() {
        return economy;
    }

    public Chat getChat() {
        return chat;
    }

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        getLogger().info("Registering Economy: " + setupEconomy());
        getLogger().info("Registering Chat: " + setupChat());


        Settings.load();
        Settings.loadLanguage();
        Settings.loadMotd();

        setupMetrics();

        registerCommands();
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new PlayerDataEvents(), this);
        getServer().getPluginManager().registerEvents(new PlayerMessageEvents(), this);
        getServer().getPluginManager().registerEvents(new SignColorEvents(), this);
        getServer().getPluginManager().registerEvents(new DamageEvents(), this);

        PlayerEvents.Refresh();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new LagTask(), 100L, 1L);

        getLogger().info(plugin.getName() + " is now enabled!");
    }

    @Override
    public void onDisable() {
        JailTask.DisableAll();
        getLogger().info(plugin.getName() + " is now disabled!");
    }

    @Override
    public void saveDefaultConfig() {
        loadConfigFromJar();
        loadLanguageFromJar(false);
        loadMotdFromJar();
    }

    private void registerCommands() {
        CommandTime commandTime = new CommandTime();
        CommandGameMode commandGameMode = new CommandGameMode();
        CommandWho commandWho = new CommandWho();
        CommandSpawnEntity commandSpawnEntity = new CommandSpawnEntity();
        CommandTPS commandTPS = new CommandTPS();
        CommandWeather commandWeather = new CommandWeather();
        CommandFundamentals commandFundamentals = new CommandFundamentals();
        CommandVanish commandVanish = new CommandVanish();

        this.getCommand("SetSpawn").setExecutor(new CommandSetSpawn());
        this.getCommand("Spawn").setExecutor(new CommandSpawn());
        this.getCommand("Back").setExecutor(new CommandBack());
        this.getCommand("TP").setExecutor(new CommandTeleport());
        this.getCommand("TPPOS").setExecutor(new CommandTeleportPosition());
        this.getCommand("TPA").setExecutor(new CommandTPA());
        this.getCommand("TPAccept").setExecutor(new CommandTPAccept());
        this.getCommand("TPDeny").setExecutor(new CommandTPDeny());
        this.getCommand("TPToggle").setExecutor(new CommandTeleportToggle());
        this.getCommand("Vanish").setExecutor(commandVanish);
        this.getCommand("V").setExecutor(commandVanish);
        this.getCommand("Msg").setExecutor(new CommandMessage());
        this.getCommand("R").setExecutor(new CommandReply());
        this.getCommand("Time").setExecutor(commandTime);
        this.getCommand("Time").setTabCompleter(commandTime);
        this.getCommand("Day").setExecutor(new CommandDay());
        this.getCommand("Night").setExecutor(new CommandNight());
        this.getCommand("Weather").setExecutor(commandWeather);
        this.getCommand("Weather").setTabCompleter(commandWeather);
        this.getCommand("Heal").setExecutor(new CommandHeal());
        this.getCommand("Kill").setExecutor(new CommandKill());
        this.getCommand("Feed").setExecutor(new CommandFeed());
        this.getCommand("CLS").setExecutor(new CommandClearChat());
        this.getCommand("GameMode").setExecutor(commandGameMode);
        this.getCommand("GameMode").setTabCompleter(commandGameMode);
        this.getCommand("GM").setExecutor(commandGameMode);
        this.getCommand("GM").setTabCompleter(commandGameMode);
        this.getCommand("Who").setExecutor(commandWho);
        this.getCommand("List").setExecutor(commandWho);
        this.getCommand("AFK").setExecutor(new CommandAFK());
        this.getCommand("SpawnEntity").setExecutor(commandSpawnEntity);
        this.getCommand("SpawnEntity").setTabCompleter(commandSpawnEntity);
        this.getCommand("SpawnMob").setExecutor(commandSpawnEntity);
        this.getCommand("SpawnMob").setTabCompleter(commandSpawnEntity);
        this.getCommand("Butcher").setExecutor(new CommandButcher());
        this.getCommand("TPS").setExecutor(commandTPS);
        this.getCommand("Lag").setExecutor(commandTPS);
        this.getCommand("Give").setExecutor(new CommandGive());
        this.getCommand("Kick").setExecutor(new CommandKick());
        this.getCommand("SocialSpy").setExecutor(new CommandSocialSpy());
        this.getCommand("God").setExecutor(new CommandGod());
        this.getCommand("Seed").setExecutor(new CommandSeed());
        this.getCommand("Fly").setExecutor(new CommandFly());
        this.getCommand("Speed").setExecutor(new CommandSpeed());
        this.getCommand("SetMaxHealth").setExecutor(new CommandSetMaxHealth());
        this.getCommand("SetJail").setExecutor(new CommandSetJail());
        this.getCommand("DelJail").setExecutor(new CommandDelJail());
        this.getCommand("Jail").setExecutor(new CommandJail());
        this.getCommand("UnJail").setExecutor(new CommandUnjail());
        this.getCommand("ListJail").setExecutor(new CommandListJail());
        this.getCommand("WhoIs").setExecutor(new CommandWhoIs());
        this.getCommand("Seen").setExecutor(new CommandSeen());
        this.getCommand("Nuke").setExecutor(new CommandNuke());
        this.getCommand("Smite").setExecutor(new CommandSmite());
        this.getCommand("Sun").setExecutor(new CommandSun());
        this.getCommand("InvSee").setExecutor(new CommandInvSee());
        this.getCommand("Enderchest").setExecutor(new CommandEnderchest());
        this.getCommand("Ban").setExecutor(new CommandBan());
        this.getCommand("Unban").setExecutor(new CommandUnban());
        this.getCommand("Burn").setExecutor(new CommandBurn());
        this.getCommand("Kit").setExecutor(new CommandKit());
        this.getCommand("Enchant").setExecutor(new CommandEnchant());
        this.getCommand("Nametag").setExecutor(new CommandNametag());
        this.getCommand("Stop").setExecutor(new CommandStop());
        this.getCommand("I").setExecutor(new CommandI());
        this.getCommand("Warp").setExecutor(new CommandWarp());
        this.getCommand("SetWarp").setExecutor(new CommandSetWarp());
        this.getCommand("DelWarp").setExecutor(new CommandDelWarp());
        this.getCommand("Home").setExecutor(new CommandHome());
        this.getCommand("SetHome").setExecutor(new CommandSetHome());
        this.getCommand("DelHome").setExecutor(new CommandDelHome());
        this.getCommand("Workbench").setExecutor(new CommandWorkbench());
        this.getCommand("Sudo").setExecutor(new CommandSudo());
        this.getCommand("Fundamentals").setExecutor(commandFundamentals);
        this.getCommand("Fundamentals").setTabCompleter(commandFundamentals);
    }

    private void setupMetrics() {
        if (Settings.AllowMetrics) {
            new MetricsLite(this);
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return (economy != null);
    }

    @Override
    public FileConfiguration getConfig() {
        File file = new File("plugins" + File.separator + getName() + File.separator + "config.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    private boolean setupChat() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    public static void LoadConfigFromJar(String path, Plugin plugin) {
        File configFile = new File(path);
        File dir = new File(configFile.getAbsolutePath().substring(0, configFile.getAbsolutePath().lastIndexOf(File.separator)));
        if (!dir.isDirectory())
            dir.mkdirs();

        if (!configFile.exists()) {
            InputStream fis = plugin.getResource("config.yml");
            FileOutputStream fos = null;
            try {
                configFile.createNewFile();
                fos = new FileOutputStream(configFile);
                byte[] buf = new byte[1024];
                int i;
                while ((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to load config from JAR");
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    plugin.getLogger().severe(e.getMessage());
                }
            }
        }
    }

    private void loadConfigFromJar() {
        File file = new File("plugins" + File.separator + getName() + File.separator + "config.yml");
        File dir = new File("plugins" + File.separator + getName());
        if (!dir.isDirectory())
            dir.mkdirs();

        if (!file.exists()) {
            exportInternalFile("config.yml", file);
        }
    }

    private void loadMotdFromJar() {
        File file = new File("plugins" + File.separator + getName() + File.separator + "motd.txt");
        File dir = new File("plugins" + File.separator + getName());
        if (!dir.isDirectory())
            dir.mkdirs();

        if (!file.exists()) {
            exportInternalFile("motd.txt", file);
        }
    }

    public void loadLanguageFromJar(boolean overwrite) {
        String languageAbbreviation = getConfig().getString("Language");

        File file = new File("plugins" + File.separator + getName() + File.separator + "language.yml");
        File dir = new File("plugins" + File.separator + getName());
        if (!dir.isDirectory())
            dir.mkdirs();

        if (!file.exists() || overwrite) {
            exportInternalFile("language-" + languageAbbreviation + ".yml", file);
        }
    }

    private void exportInternalFile(String resource, File output) {
        InputStream fis = plugin.getResource(resource);
        FileOutputStream fos = null;
        try {
            output.createNewFile();
            fos = new FileOutputStream(output);
            byte[] buf = new byte[1024];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            getLogger().severe("Failed to load config from JAR!");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                getLogger().severe(e.getMessage());
            }
        }
    }
}
