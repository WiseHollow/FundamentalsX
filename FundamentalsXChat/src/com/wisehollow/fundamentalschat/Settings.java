package com.wisehollow.fundamentalschat;

import com.wisehollow.fundamentals.utils.SettingsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by John on 10/15/2016.
 */
public class Settings implements Listener {
    public static boolean UseEmojiChat = true;
    public static boolean UsePlayerMention = true;
    public static String chatFormat;

    public static boolean UseAffixes = false;
    private static HashMap<String, String> affixHoverMessages = new HashMap<>();

    private static void load() {
        FileConfiguration configuration = com.wisehollow.fundamentals.Main.getPlugin().getConfig();

        UseEmojiChat = configuration.getBoolean("Emoji_Chat");
        UsePlayerMention = configuration.getBoolean("Player_Mention");
        chatFormat = ChatColor.translateAlternateColorCodes('&', SettingsUtil.getValueFromConfiguration("Chat_Format", String.class));

        UseAffixes = configuration.getBoolean("Use_Affixes");
        ConfigurationSection section = configuration.getConfigurationSection("Affixes");
        for (String key : section.getKeys(false)) {
            String value = section.getString(key);
            affixHoverMessages.put(key, value);
        }

        if (UseEmojiChat) {
            Main.getPlugin().getLogger().info("Registering Emojis: " + EmojiChat.Load());
        }
    }

    @EventHandler
    public void loadSettingsWhenFundamentalsLoads(PluginEnableEvent event) {
        if (event.getPlugin().getName().equalsIgnoreCase("FundamentalsX")) {
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), Settings::load, 1L);
        }
    }

    public static Optional<String> getHoverMessage(String affix) {
        if (affixHoverMessages.containsKey(affix))
            return Optional.of(affixHoverMessages.get(affix));
        else
            return Optional.empty();
    }
}
