package com.wisehollow.fundamentalscombat;

public class Settings {

    public static boolean delayedPVPForNewPlayers;
    public static int delayedPVPMinutes;

    public static boolean enableLegacyCombat;
    public static boolean preventCombatLogging;

    public static boolean playersDropHead = false;
    public static float playersDropHeadChance = 0.2f;

    public static void load() {
        delayedPVPForNewPlayers = Main.getPlugin().getConfig().getBoolean("Delayed_PVP_For_New_Players");
        delayedPVPMinutes = Main.getPlugin().getConfig().getInt("Delayed_PVP_Minutes");
        enableLegacyCombat = Main.getPlugin().getConfig().getBoolean("Enable_Legacy_Combat");
        preventCombatLogging = Main.getPlugin().getConfig().getBoolean("Prevent_CombatLogging");
        playersDropHead = Main.getPlugin().getConfig().getBoolean("Players_Drop_Head");
        playersDropHeadChance = (float) Main.getPlugin().getConfig().getDouble("Players_Drop_Head_Chance");

        if (enableLegacyCombat)
            Main.getPlugin().getLogger().info("Enable Legacy Combat: true");
        if (preventCombatLogging)
            Main.getPlugin().getLogger().info("Prevent Combat-Logging: true");
        if (playersDropHead)
            Main.getPlugin().getLogger().info("Players Drop Heads: true");
    }


}
