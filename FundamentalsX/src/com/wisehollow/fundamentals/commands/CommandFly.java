package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
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
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Fly")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length > 0 && sender.hasPermission("Fundamentals.Fly.Other")) {
            player = PlayerUtil.GetPlayer(args[0]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }
        }

        player.setAllowFlight(!player.getAllowFlight());
        if (player.getAllowFlight()) {
            player.sendMessage(Language.getInstance().flightEnabled);
            if (!sender.equals(player))
                sender.sendMessage(Language.getInstance().flightEnabledFor.replace("%p", player.getName()));
        } else {
            player.sendMessage(Language.getInstance().flightDisabled);
            if (!sender.equals(player))
                sender.sendMessage(Language.getInstance().flightDisabledFor.replace("%p", player.getName()));
        }


        return true;
    }
}
