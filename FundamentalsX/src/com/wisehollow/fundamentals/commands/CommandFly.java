package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandFly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Fly")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length > 0 && sender.hasPermission("Fundamentals.Fly.Other")) {
            player = PlayerUtil.GetPlayer(args[0]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.PlayerMustBeLoggedIn);
                return true;
            }
        }

        player.setAllowFlight(!player.getAllowFlight());
        if (player.getAllowFlight()) {
            player.sendMessage(Language.PREFIX + "Flight enabled!");
            if (!sender.equals(player))
                sender.sendMessage(Language.PREFIX + "You enabled flight for: " + player.getName());
        } else {
            player.sendMessage(Language.PREFIX + "Flight disabled!");
            if (!sender.equals(player))
                sender.sendMessage(Language.PREFIX + "You disabled flight for: " + player.getName());
        }


        return true;
    }
}
