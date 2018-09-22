package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by John on 10/13/2016.
 */
public class CommandTime implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Time")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        World world;

        if (!(sender instanceof Player)) {
            //TODO: Handle as server console.

            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        } else {
            world = ((Player) sender).getWorld();
        }

        if (args.length == 0) {
            long time = world.getTime();
            sender.sendMessage(Language.getInstance().timeCurrent.replace("%t", Long.toString(time)));

            //int hours = 6 + (int) (world.getTime() / 1000);
            return true;
        }

        if (args[0].equalsIgnoreCase("Day")) {
            world.setTime(1000);
            sender.sendMessage(Language.getInstance().timeSet.replace("%t", "7:00am"));
        } else if (args[0].equalsIgnoreCase("Night")) {
            world.setTime(13000);
            sender.sendMessage(Language.getInstance().timeSet.replace("%t", "7:00pm"));
        } else {
            return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String[] tabs = new String[] { "day", "night" };
        return Arrays.stream(tabs).collect(Collectors.toList());
    }
}
