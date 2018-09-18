package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.PlayerUtil;
import com.wisehollow.fundamentals.tasks.GodTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandGod implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.God")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length > 0 && sender.hasPermission("Fundamentals.God.Other")) {
            player = PlayerUtil.GetPlayer(args[0]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.PlayerMustBeLoggedIn);
                return true;
            }
        }

        GodTask task = GodTask.GetTask(player);

        if (task == null) {
            player.sendMessage(Language.PREFIX + "God-mode enabled!");
            if (!sender.equals(player))
                sender.sendMessage(Language.PREFIX + "You enabled God Mode for: " + player.getName());
            task = new GodTask(player);
            task.run();
        } else {
            player.sendMessage(Language.PREFIX + "God-mode disabled!");
            if (!sender.equals(player))
                sender.sendMessage(Language.PREFIX + "You disabled God Mode for: " + player.getName());
            task.disable();
        }

        return true;
    }
}
