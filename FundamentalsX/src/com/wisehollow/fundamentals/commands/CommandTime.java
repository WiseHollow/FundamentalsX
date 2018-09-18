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
public class CommandTime implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Time")) {
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

        if (args.length == 0) {
            long time = world.getTime();
            sender.sendMessage(Language.PREFIX + "The current time is: " + time + " ticks.");

            //int hours = 6 + (int) (world.getTime() / 1000);
            return true;
        }

        if (args[0].equalsIgnoreCase("Day")) {
            world.setTime(1000);
            sender.sendMessage(Language.PREFIX + "Time set to 7:00am.");
        } else if (args[0].equalsIgnoreCase("Night")) {
            world.setTime(13000);
            sender.sendMessage(Language.PREFIX + "Time set to 7:00pm.");
        } else {
            return false;
        }

        return true;
    }
}
