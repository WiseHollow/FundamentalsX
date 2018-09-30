package com.wisehollow.fundamentals;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by John on 10/13/2016.
 */
public class Settings {

    private static FileConfiguration config = Main.getPlugin().getConfig();
    public static String languageAbbreviation = "en";
    public static String motd = "";
    public static int TeleportDelay = 0; // In seconds
    public static Location Spawn = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
    public static Location SpawnFirstJoin = null;
    public static boolean EnableSpawnFirstJoin = true;
    public static int AFKDelay = 0;
    public static boolean SignColor = false;
    public static HashMap<String, Location> jails = new HashMap<>();
    public static String JoinMessage = "%p has joined the game.";
    public static String QuitMessage = "%p has left the game.";
    public static boolean AllowUnsafeEnchantments = false;
    public static String StarterKit = "None";
    public static String ShutdownMessage = "Server will shutdown in %m minutes";
    public static Boolean AllowMetrics = true;
    public static int DefaultSetHomeAmount = 1;
    public static int SpawnMobLimit = 10;

    public static boolean PreventFireDamage = false;
    public static boolean PreventLavaDamage = false;

    private static HashMap<String, Integer> SetHomeCountPermissions = new HashMap<>();

    public static boolean HasPermissionForHomeAmount(Player player, int request) {
        if (player.hasPermission("Fundamentals.Homes.*") || request <= DefaultSetHomeAmount)
            return true;

        for (String key : SetHomeCountPermissions.keySet()) {
            if (player.hasPermission("Fundamentals.Homes." + key) && SetHomeCountPermissions.get(key) >= request)
                return true;
        }

        return false;
    }

    public static HashMap<String, Location> warps = new HashMap<>();

    public static void loadMotd() {
        File file = new File("plugins" + File.separator + Main.getPlugin().getName() + File.separator + "motd.txt");
        if (file.exists()) {
            motd = "";
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    motd += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static void loadLanguage() {
        Language.getInstance();
    }

    public static void load() {
        languageAbbreviation = config.getString("Language");
        TeleportDelay = config.getInt("Teleport_Delay");
        AFKDelay = config.getInt("Afk_Delay");
        SignColor = config.getBoolean("Sign_Colors");
        JoinMessage = ChatColor.translateAlternateColorCodes('&', config.getString("Join_Message"));
        QuitMessage = ChatColor.translateAlternateColorCodes('&', config.getString("Quit_Message"));
        AllowUnsafeEnchantments = config.getBoolean("Allow_Unsafe_Enchantments");
        StarterKit = config.getString("Starter_Kit");
        ShutdownMessage = config.getString("Scheduled_Shutdown_Message");
        AllowMetrics = config.getBoolean("Allow_Metrics");
        DefaultSetHomeAmount = config.getInt("Default_SetHomes");
        EnableSpawnFirstJoin = config.getBoolean("First_Join");
        PreventFireDamage = config.getBoolean("Prevent_Fire_Damage");
        PreventLavaDamage = config.getBoolean("Prevent_Lava_Damage");
        SpawnMobLimit = config.getInt("Spawn_Mob_Limit");

        for (String s : config.getStringList("Home_Permissions")) {
            String[] keys = s.split(" ");
            SetHomeCountPermissions.put(keys[0], Integer.parseInt(keys[1]));
        }

        if (config.getConfigurationSection("Spawn_Location") != null) {
            World world = Bukkit.getWorld(config.getString("Spawn_Location.World"));
            if (world != null)
                Spawn = new Location(world, config.getInt("Spawn_Location.X"), config.getInt("Spawn_Location.Y"), config.getInt("Spawn_Location.Z"), (float) config.getDouble("Spawn_Location.Yaw"), (float) config.getDouble("Spawn_Location.Pitch"));
        }

        if (config.getConfigurationSection("Spawn_Location_First_Join") != null) {
            World world = Bukkit.getWorld(config.getString("Spawn_Location_First_Join.World"));
            if (world != null)
                SpawnFirstJoin = new Location(world, config.getInt("Spawn_Location_First_Join.X"), config.getInt("Spawn_Location_First_Join.Y"), config.getInt("Spawn_Location_First_Join.Z"), (float) config.getDouble("Spawn_Location_First_Join.Yaw"), (float) config.getDouble("Spawn_Location_First_Join.Pitch"));
        }

        if (config.getConfigurationSection("Jails") != null) {
            for (String key : config.getConfigurationSection("Jails").getKeys(false)) {
                World world = Bukkit.getWorld(config.getString("Jails." + key + ".Location.World"));
                int x = config.getInt("Jails." + key + ".Location.X");
                int y = config.getInt("Jails." + key + ".Location.Y");
                int z = config.getInt("Jails." + key + ".Location.Z");
                Location loc = new Location(world, x, y, z);
                jails.put(key, loc);
            }
        }

        if (config.getConfigurationSection("Kits") != null) {
            ConfigurationSection kits = config.getConfigurationSection("Kits");
            for (String key : kits.getKeys(false)) {
                String name = key;
                int delay = kits.getInt(key + ".delay");
                List<String> itemStrings = kits.getStringList(key + ".items");

                if (name == null || name.equalsIgnoreCase("") || delay < 0)
                    continue;

                Kit kit = new Kit(name, delay);

                if (itemStrings != null && !itemStrings.isEmpty()) {
                    for (String s : itemStrings) {
                        String[] args = s.split(" ");
                        Material mat;
                        int amount;

                        String displayName = null;
                        String lore = null;

                        HashMap<Enchantment, Integer> enchantments = new HashMap<>();

                        try {
                            mat = Material.getMaterial(args[0].toUpperCase());
                            amount = Integer.valueOf(args[1]);

                            if (mat == null) {
                                Main.getPlugin().getLogger().warning("Could not load material from kit: " + args[0].toUpperCase());
                                continue;
                            }

                            if (args.length > 2) {
                                displayName = args[2];
                            }
                            if (args.length > 3) {
                                lore = args[3];
                            }

                            if (args.length > 4) {
                                for (int i = 4; i < args.length; i++) {
                                    String[] eElement = args[i].split("%");
                                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(eElement[0].toLowerCase()));
                                    int level = Integer.valueOf(eElement[1]);
                                    enchantments.put(enchantment, level);
                                }
                            }
                        } catch (Exception ex) {
                            Main.getPlugin().getLogger().severe(ex.getMessage());
                            continue;
                        }

                        ItemStack item = new ItemStack(mat, amount);
                        ItemMeta meta = item.getItemMeta();
                        if (displayName != null && !displayName.equalsIgnoreCase("-"))
                            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName.replaceAll("_", " ")));
                        if (lore != null && !lore.equalsIgnoreCase("-")) {
                            List<String> loreList = new ArrayList<>(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore.replaceAll("_", " ")).split("%n")));
                            meta.setLore(loreList);
                        }

                        item.setItemMeta(meta);

                        if (!enchantments.isEmpty()) {
                            for (Enchantment enchantment : enchantments.keySet()) {
                                if (!AllowUnsafeEnchantments)
                                    item.addEnchantment(enchantment, enchantments.get(enchantment));
                                else
                                    item.addUnsafeEnchantment(enchantment, enchantments.get(enchantment));
                            }
                        }

                        kit.addItem(item);
                    }
                }

                Kit.AddKit(kit);
            }
        }

        File warpFile = new File("plugins" + File.separator + "FundamentalsX" + File.separator + "warps.yml");
        if (warpFile.exists()) {
            YamlConfiguration warpConfig = YamlConfiguration.loadConfiguration(warpFile);
            if (warpConfig.getConfigurationSection("Warps") != null) {
                ConfigurationSection section = warpConfig.getConfigurationSection("Warps");
                for (String key : section.getKeys(false)) {
                    int x;
                    int y;
                    int z;
                    float pitch;
                    float yaw;

                    x = section.getInt(key + ".Location.X");
                    y = section.getInt(key + ".Location.Y");
                    z = section.getInt(key + ".Location.Z");
                    pitch = (float) section.getDouble(key + ".Location.Pitch");
                    yaw = (float) section.getDouble(key + ".Location.Yaw");

                    Location loc = new Location(Bukkit.getWorld(section.getString(key + ".Location.World")), x, y, z, yaw, pitch);

                    warps.put(key, loc);
                }
            }
        }
    }

    public static void Save() {
        //
        // General configuration
        //

        config.set("Teleport_Delay", TeleportDelay);
        config.set("Afk_Delay", AFKDelay);
        config.set("Spawn_Location.World", Spawn.getWorld().getName());
        config.set("Spawn_Location.X", Spawn.getBlockX());
        config.set("Spawn_Location.Y", Spawn.getBlockY());
        config.set("Spawn_Location.Z", Spawn.getBlockZ());
        config.set("Spawn_Location.Yaw", Spawn.getYaw());
        config.set("Spawn_Location.Pitch", Spawn.getPitch());

        //
        // Jail saves
        //

        config.set("Jails", null);
        for (String key : jails.keySet()) {
            Location loc = jails.get(key);
            config.set("Jails." + key + ".Location.World", loc.getWorld().getName());
            config.set("Jails." + key + ".Location.X", loc.getBlockX());
            config.set("Jails." + key + ".Location.Y", loc.getBlockY());
            config.set("Jails." + key + ".Location.Z", loc.getBlockZ());
        }

        //
        // Warp saves
        //

        File warpFile = new File("plugins" + File.separator + "FundamentalsX" + File.separator + "warps.yml");
        if (!warpFile.exists()) {
            try {
                warpFile.createNewFile();
            } catch (IOException ex) {
                Main.getPlugin().getLogger().severe(ex.getMessage());
            }
        }
        YamlConfiguration warpConfig = YamlConfiguration.loadConfiguration(warpFile);
        for (String key : warps.keySet()) {
            Location loc = warps.get(key);
            warpConfig.set("Warps." + key + ".Location.World", loc.getWorld().getName());
            warpConfig.set("Warps." + key + ".Location.X", loc.getBlockX());
            warpConfig.set("Warps." + key + ".Location.Y", loc.getBlockY());
            warpConfig.set("Warps." + key + ".Location.Z", loc.getBlockZ());
            warpConfig.set("Warps." + key + ".Location.Yaw", loc.getYaw());
            warpConfig.set("Warps." + key + ".Location.Pitch", loc.getPitch());
        }

        //
        // Save to file
        //

        try {
            warpConfig.save(warpFile);
        } catch (Exception ex) {
            Main.getPlugin().getLogger().severe(ex.getMessage());
        }

        Main.getPlugin().saveConfig();
    }
}
