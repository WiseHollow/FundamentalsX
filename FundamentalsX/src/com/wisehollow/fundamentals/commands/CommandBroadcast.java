package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandBroadcast implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("Fundamentals.Broadcast")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length != 0) {
            String message = String.join(" ", args);
            Bukkit.getServer().broadcastMessage(Language.getInstance().broadcast.replace("%b", message));
            return true;
        }

        return false;
    }
}
