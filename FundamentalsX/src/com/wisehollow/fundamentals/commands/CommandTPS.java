package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.tasks.LagTask;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandTPS implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.TPS")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        double tps = LagTask.getTPS();
        double lag = Math.round((1.0D - tps / 20.0D) * 100.0D);

        Runtime runtime = Runtime.getRuntime();
        double mult = 1.00d / 1024.00d;
        long maxMemory = (long) (runtime.maxMemory() * mult * mult);
        long allocatedMemory = (long) (runtime.totalMemory() * mult * mult);
        long freeMemory = (long) (runtime.freeMemory() * mult * mult);
        double percentTPS = round(100.00d - lag, 2);
        tps = round(tps, 2);

        sender.sendMessage(Language.getInstance().serverStatistics);
        sender.sendMessage(Language.getInstance().currentTPS
                .replace("%a", Double.toString(tps))
                .replace("%p", Double.toString(percentTPS)));
        sender.sendMessage(Language.getInstance().maximumMemory.replace("%m", Long.toString(maxMemory)));
        sender.sendMessage(Language.getInstance().allocatedMemory.replace("%m", Long.toString(allocatedMemory)));
        sender.sendMessage(Language.getInstance().freeMemory.replace("%m", Long.toString(freeMemory)));

        if (sender instanceof Player) {
            for (World w : Bukkit.getWorlds()) {
                TextComponent message = new TextComponent(Language.getInstance().prefixInfo + "" + ChatColor.BOLD + "    > " + w.getName() + ChatColor.RESET);
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.ITALIC + Language.getInstance().entityList + ChatColor.RESET + w.getEntities().size() +
                        "\n" + ChatColor.ITALIC + Language.getInstance().livingEntityList + ChatColor.RESET + w.getLivingEntities().size() +
                        "\n" + ChatColor.ITALIC + Language.getInstance().chunkList + ChatColor.RESET + w.getLoadedChunks().length).create()));
                ((Player) sender).spigot().sendMessage(message);
            }
        } else {
            for (World w : Bukkit.getWorlds()) {
                sender.sendMessage(Language.getInstance().prefixInfo + ChatColor.BOLD + w.getName() + ChatColor.RESET);
                sender.sendMessage("    " + ChatColor.ITALIC + Language.getInstance().entityList + ChatColor.RESET + w.getEntities().size());
                sender.sendMessage("    " + ChatColor.ITALIC + Language.getInstance().chunkList + ChatColor.RESET + w.getLoadedChunks().length);
            }
        }

        return true;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
