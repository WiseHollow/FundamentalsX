package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandSun implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Weather")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        World world;

        if (!(sender instanceof Player)) {
            //TODO: Handle as server console.

            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        } else {
            world = ((Player) sender).getWorld();
        }

        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(24000 * 3);
        sender.sendMessage(Language.PREFIX + "Weather set to sunny.");

        return true;
    }
}
