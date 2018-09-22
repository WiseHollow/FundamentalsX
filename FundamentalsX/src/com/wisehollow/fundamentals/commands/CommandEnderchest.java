package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.tasks.EnderchestSeeTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEnderchest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        if (!sender.hasPermission("Fundamentals.Enderchest")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        Player target;

        if (args.length > 0 && !sender.hasPermission("Fundamentals.Enderchest.Other")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        } else if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
        } else
            target = (Player) sender;

        if (target == null) {
            sender.sendMessage(Language.getInstance().targetNotOnline);
            return true;
        }

        if (!sender.equals(target))
            sender.sendMessage(Language.getInstance().viewingEnderchest.replace("%p", target.getName()));
        EnderchestSeeTask task = new EnderchestSeeTask((Player) sender, target);
        task.run();
        return true;
    }
}
