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
public class CommandWeather implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Weather")) {
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
            String weather;
            if (world.hasStorm()) {
                weather = "Raining";
                if (world.isThundering())
                    weather += "/Storming";
            } else
                weather = "Sunny";
            sender.sendMessage(Language.getInstance().weatherCurrent.replace("%t", weather));
            return true;
        }

        if (args[0].equalsIgnoreCase("Sun") || args[0].equalsIgnoreCase("Clear")) {
            world.setStorm(false);
            world.setThundering(false);
            world.setWeatherDuration(24000 * 3);
            sender.sendMessage(Language.getInstance().weatherSetSun);
        } else if (args[0].equalsIgnoreCase("Rain")) {
            world.setStorm(true);
            world.setThundering(false);
            world.setWeatherDuration(24000 * 3);
            world.setThunderDuration(24000 * 3);
            sender.sendMessage(Language.getInstance().weatherSetRain);
        } else if (args[0].equalsIgnoreCase("Storm")) {
            world.setStorm(true);
            world.setThundering(true);
            world.setWeatherDuration(24000 * 3);
            world.setThunderDuration(24000 * 3);
            sender.sendMessage(Language.getInstance().weatherSetStorm);
        } else {
            return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String[] tabs = new String[] { "sun", "rain", "storm" };
        return Arrays.stream(tabs).collect(Collectors.toList());
    }
}
