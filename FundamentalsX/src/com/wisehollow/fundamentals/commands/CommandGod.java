package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
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
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.God")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length > 0 && sender.hasPermission("Fundamentals.God.Other")) {
            player = PlayerUtil.GetPlayer(args[0]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }
        }

        GodTask task = GodTask.GetTask(player);

        if (task == null) {
            player.sendMessage(Language.getInstance().godModeEnabled);
            if (!sender.equals(player))
                sender.sendMessage(Language.getInstance().godModeEnabledFor.replace("%p", player.getName()));
            task = new GodTask(player);
            task.run();
        } else {
            player.sendMessage(Language.getInstance().godModeDisabled);
            if (!sender.equals(player))
                sender.sendMessage(Language.getInstance().godModeDisabledFor.replace("%p", player.getName()));
            task.disable();
        }

        return true;
    }
}
