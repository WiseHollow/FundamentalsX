package com.wisehollow.fundamentalschat.listeners;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentalschat.EmojiChat;
import com.wisehollow.fundamentalschat.Main;
import com.wisehollow.fundamentalschat.Settings;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by John on 10/14/2016.
 */
public class ChatEvents implements Listener {

    private TextComponent createAffixTextComponent(String prefix, String hoverMessage) {
        TextComponent prefixComponent = new TextComponent(ChatColor.translateAlternateColorCodes('&', prefix));
        prefixComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage).create()));
        return prefixComponent;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void FormatPrefix(AsyncPlayerChatEvent event) {
        if (!event.isCancelled() && Main.getChat() != null) {
            String[] playerGroups = Main.getChat().getPlayerGroups(event.getPlayer());
            String format = "<%1$s> %2$s";

            if (Settings.UseAffixes && playerGroups.length > 0) {
                final TextComponent textComponent = new TextComponent("");
                TextComponent contentsComponent = new TextComponent(format.replace("%1$s", event.getPlayer().getDisplayName()).replace("%2$s", event.getMessage()));
                List<String> playerPrefixes = new ArrayList<>();
                Arrays.stream(playerGroups).forEach(group -> {
                    String prefix = Main.getChat().getGroupPrefix(event.getPlayer().getWorld(), group);
                    if (prefix != null)
                        playerPrefixes.add(prefix);
                });

                playerPrefixes.forEach(prefix -> {
                    Optional<String> optionalHoverMessage = Settings.getHoverMessage(prefix);
                    if (optionalHoverMessage.isPresent()) {
                        String hoverMessage = ChatColor.translateAlternateColorCodes('&', optionalHoverMessage.get());
                        TextComponent prefixComponent = createAffixTextComponent(prefix, hoverMessage);
                        textComponent.addExtra(prefixComponent);
                    }
                });

                textComponent.addExtra(contentsComponent);

                event.getRecipients().forEach(recipient -> recipient.spigot().sendMessage(textComponent));
                event.getRecipients().clear();
            } else {
                String[] gPrefixes = Main.getChat().getPlayerGroups(event.getPlayer());
                String gPrefix;
                String uPrefix = Main.getChat().getPlayerPrefix(event.getPlayer());
                if (gPrefixes.length > 0) {
                    gPrefix = Main.getChat().getGroupPrefix(event.getPlayer().getWorld(), gPrefixes[0]);
                    format = ChatColor.translateAlternateColorCodes('&', gPrefix) + format;
                } else if (uPrefix != null) {
                    format = ChatColor.translateAlternateColorCodes('&', uPrefix) + format;
                }

                event.setFormat(format);
            }
        }
    }

    @EventHandler
    public void EmojiConvert(AsyncPlayerChatEvent event) {
        if (event.isCancelled() || !Settings.UseEmojiChat)
            return;

        event.setMessage(EmojiChat.Convert(event.getMessage()));
    }

    @EventHandler
    public void ColorChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled())
            return;
        //TODO: PERMISSIONS
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
    }

    @EventHandler
    public void PlayerMention(AsyncPlayerChatEvent event) {
        if (event.isCancelled() || !Settings.UsePlayerMention)
            return;

        List<Player> mentioned = new ArrayList<>();

        String[] msg = event.getMessage().split(" ");
        for (String s : msg) {
            if (s.startsWith("@")) {
                Player target = Bukkit.getPlayer(s.replaceAll("@", ""));
                if (target != null && !mentioned.contains(target)) {
                    mentioned.add(target);
                    target.sendMessage(Language.getInstance().mentioned.replace("%p", event.getPlayer().getName()));
                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                }
            }
        }
    }
}
