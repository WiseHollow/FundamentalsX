package com.wisehollow.fundamentals.userdata;

import com.wisehollow.fundamentals.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by John on 10/20/2016.
 */
public class PlayerData implements Listener {
    private static final String directory = "plugins" + File.separator + "FundamentalsX" + File.separator + "Player Data";
    private static List<PlayerData> data = new ArrayList<>();

    public static PlayerData GetPlayerData(Player player) {
        for (PlayerData pd : data)
            if (pd.uuid.equalsIgnoreCase(player.getUniqueId().toString()))
                return pd;
        return null;
    }

    public static PlayerData GetPlayerData(String uuid) {
        for (PlayerData pd : data)
            if (pd.uuid.equalsIgnoreCase(uuid))
                return pd;
        return null;
    }

    public static void UnloadPlayerData(Player player) {
        data.remove(GetPlayerData(player));
    }

    public static void UnloadPlayerData(PlayerData player) {
        data.remove(player);
    }

    public static Optional<PlayerData> LoadPlayerData(OfflinePlayer player) {
        File dir = new File(directory);
        if (!dir.isDirectory())
            dir.mkdirs();
        File file = new File(directory + File.separator + player.getUniqueId().toString() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Main.getPlugin().getLogger().info("Creating player data for: " + player.getName() + " ID: " + player.getUniqueId().toString());
            } catch (IOException ex) {
                Main.getPlugin().getLogger().severe(ex.getMessage());
                return Optional.empty();
            }
        }

        PlayerData profile = new PlayerData(player instanceof Player ? ((Player) player) : player);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        Boolean teleportDisabled = false;
        if (config.isBoolean("TeleportDisabled")) {
            teleportDisabled = config.getBoolean("TeleportDisabled");
        }
        profile.teleportDisabled = teleportDisabled;

        if (config.getConfigurationSection("LastPosition") != null) {
            World world = Bukkit.getWorld(config.getString("LastPosition.Location.World"));
            int x = config.getInt("LastPosition.Location.X");
            int y = config.getInt("LastPosition.Location.Y");
            int z = config.getInt("LastPosition.Location.Z");
            float pitch = (float) config.getDouble("LastPosition.Location.Pitch");
            float yaw = (float) config.getDouble("LastPosition.Location.Yaw");

            Location lastPosition = new Location(world, x, y, z, yaw, pitch);
            profile.lastLocation = lastPosition;
        }

        if (config.getConfigurationSection("Homes") != null) {
            HashMap<String, Location> homes = new HashMap<>();
            for (String key : config.getConfigurationSection("Homes").getKeys(false)) {
                World world = Bukkit.getWorld(config.getString("Homes." + key + ".Location.World"));
                int x = config.getInt("Homes." + key + ".Location.X");
                int y = config.getInt("Homes." + key + ".Location.Y");
                int z = config.getInt("Homes." + key + ".Location.Z");
                float pitch = (float) config.getDouble("Homes." + key + ".Location.Pitch");
                float yaw = (float) config.getDouble("Homes." + key + ".Location.Yaw");

                Location loc = new Location(world, x, y, z, yaw, pitch);
                homes.put(key, loc);
            }

            profile.setHomes(homes);

        }

        Bukkit.getPluginManager().registerEvents(profile, Main.getPlugin());
        data.add(profile);
        return Optional.of(profile);
    }

    private String uuid;
    private String name;
    private HashMap<String, Location> homes;
    private Location lastLocation;
    private Boolean teleportDisabled;

    public PlayerData(OfflinePlayer player) {
        this.uuid = player.getUniqueId().toString();
        this.name = player.getName();
        this.homes = new HashMap<>();
        this.teleportDisabled = false;
        if (player.isOnline()) {
            this.lastLocation = ((Player) player).getLocation();
        }
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Location> getHomes() {
        return homes;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public boolean setHome(String name) {
        name = name.toLowerCase();
        Player player = Bukkit.getPlayer(this.name);
        if (player == null)
            return false;
        Location loc = player.getLocation();
        homes.put(name.toLowerCase(), loc);
        save();
        return true;
    }

    public boolean deleteHome(String name) {
        name = name.toLowerCase();
        if (!homes.containsKey(name))
            return false;

        homes.remove(name);
        save();
        return true;
    }

    public Location getHome(String name) {
        name = name.toLowerCase();
        if (!homes.containsKey(name))
            return null;
        return homes.get(name);
    }

    public void setTeleportDisabled(boolean disabled) {
        this.teleportDisabled = disabled;
        save();
    }

    public Boolean hasTeleportDisabled() {
        return teleportDisabled;
    }

    public void save() {
        File dir = new File(directory);
        if (!dir.isDirectory())
            dir.mkdirs();
        File file = new File(directory + File.separator + uuid + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Main.getPlugin().getLogger().info("Creating player data for: " + name + " ID: " + uuid);
            } catch (IOException ex) {
                Main.getPlugin().getLogger().severe(ex.getMessage());
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (teleportDisabled || config.isBoolean("TeleportDisabled")) {
            config.set("TeleportDisabled", teleportDisabled);
        }

        config.set("LastPosition.Location.World", lastLocation.getWorld().getName());
        config.set("LastPosition.Location.X", lastLocation.getBlockX());
        config.set("LastPosition.Location.Y", lastLocation.getBlockY());
        config.set("LastPosition.Location.Z", lastLocation.getBlockZ());
        config.set("LastPosition.Location.Yaw", lastLocation.getYaw());
        config.set("LastPosition.Location.Pitch", lastLocation.getPitch());

        config.set("Homes", null);
        for (String key : homes.keySet()) {
            Location loc = homes.get(key);
            config.set("Homes." + key + ".Location.World", loc.getWorld().getName());
            config.set("Homes." + key + ".Location.X", loc.getBlockX());
            config.set("Homes." + key + ".Location.Y", loc.getBlockY());
            config.set("Homes." + key + ".Location.Z", loc.getBlockZ());
            config.set("Homes." + key + ".Location.Yaw", loc.getYaw());
            config.set("Homes." + key + ".Location.Pitch", loc.getPitch());
        }

        try {
            config.save(file);
        } catch (IOException ex) {
            Main.getPlugin().getLogger().severe(ex.getMessage());
            return;
        }
    }

    private void setHomes(HashMap<String, Location> homes) {
        this.homes = homes;
    }

    @EventHandler
    public void SaveOnExit(PlayerQuitEvent event) {
        save();
        UnloadPlayerData(this);
        event.getHandlers().unregister(this);
    }
}
