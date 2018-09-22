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
public class CommandNight implements CommandExecutor {
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

        world.setTime(13000);
        sender.sendMessage(Language.getInstance().timeSet.replace("%t", "7:00pm"));
        return true;
    }
}
