package com.wisehollow.fundamentalsantigrief;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by John on 10/22/2016.
 */
public class Settings {
    public static boolean PreventFireSpread = false;
    public static boolean PreventExplosiveBlockDamage = false;
    public static boolean PreventEntityExplosion = false;
    public static boolean PreventFireCreation = false;
    public static boolean PreventEndermanGrief = false;

    public static void Load() {
        FileConfiguration configuration = Main.getPlugin().getConfig();

        PreventFireSpread = configuration.getBoolean("Prevent_FireSpread");
        PreventEntityExplosion = configuration.getBoolean("Prevent_Entity_Explosion");
        PreventExplosiveBlockDamage = configuration.getBoolean("Prevent_Explosive_BlockDamage");
        PreventFireCreation = configuration.getBoolean("Prevent_Fire_Creation");
        PreventEndermanGrief = configuration.getBoolean("Prevent_Enderman_Grief");

        if (PreventFireSpread)
            Main.getPlugin().getLogger().info("Blocking Fire Spread: true");
        if (PreventEntityExplosion)
            Main.getPlugin().getLogger().info("Blocking Entity Explosion: true");
        if (PreventExplosiveBlockDamage)
            Main.getPlugin().getLogger().info("Blocking Explosion Block Damage: true");
        if (PreventFireCreation)
            Main.getPlugin().getLogger().info("Blocking Fire Creation: true");
        if (PreventEndermanGrief)
            Main.getPlugin().getLogger().info("Blocking Enderman Grief: true");
    }
}
