package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNickname implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String nickname = null;
        Player target = null;
        if (args.length == 2) {
            if (!sender.hasPermission("Fundamentals.Nickname.Other")) {
                sender.sendMessage(Language.getInstance().unauthorized);
                return true;
            }
            target = Bukkit.getPlayer(args[0]);
            nickname = args[1];
        } else if (args.length == 1 && sender instanceof Player) {
            if (!sender.hasPermission("Fundamentals.Nickname")) {
                sender.sendMessage(Language.getInstance().unauthorized);
                return true;
            }
            target = (Player) sender;
            nickname = args[0];
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        if (target == null) {
            sender.sendMessage(Language.getInstance().targetNotOnline);
            return true;
        } else if (nickname != null && nickname.length() > 20) {
            sender.sendMessage("That nickname is too long (Max: 20 characters).");
            return true;
        } else if (nickname != null) {
            if (nickname.equalsIgnoreCase("none") || nickname.equalsIgnoreCase("off")) {
                removeNickname(target);
            } else {
                setNickname(target, nickname);
            }
        }

        return false;
    }

    private void setNickname(final Player player, final String nickname) {
        player.setDisplayName(nickname);
        player.sendMessage("Your nickname was set to: " + nickname);
    }

    private void removeNickname(final Player player) {
        player.setDisplayName(player.getName());
        player.sendMessage("Your nickname has been removed.");
    }
}
