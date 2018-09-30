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
public class CommandFeed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Feed")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length > 0 && sender.hasPermission("Fundamentals.Feed.Other")) {
            Player target = PlayerUtil.GetPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }

            target.setFoodLevel(20);
            player.sendMessage(Language.getInstance().fedPlayer.replace("%p", target.getName()));
            target.sendMessage(Language.getInstance().fed);
        } else {
            player.setFoodLevel(20);
            player.sendMessage(Language.getInstance().fed);
        }

        return true;
    }
}
