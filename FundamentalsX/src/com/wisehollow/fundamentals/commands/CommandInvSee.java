package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.PlayerUtil;
import com.wisehollow.fundamentals.tasks.InventorySeeTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandInvSee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }
        if (!sender.hasPermission("Fundamentals.InvSee")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(Language.PlayerMustBeLoggedIn);
            return true;
        }

        sender.sendMessage(Language.PREFIX + "Viewing " + target.getName() + "'s inventory.");
        InventorySeeTask task = new InventorySeeTask((Player) sender, target);
        task.run();

        return true;
    }
}
