package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import com.wisehollow.fundamentals.tasks.JailTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandUnjail implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Jail")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length != 1)
            return false;

        Player player = PlayerUtil.GetPlayer(args[0]);
        if (player == null || !player.isOnline()) {
            sender.sendMessage(Language.getInstance().targetNotOnline);
            return true;
        }

        JailTask task = JailTask.GetTask(player);
        if (task == null) {
            sender.sendMessage(Language.getInstance().notJailed);
            return true;
        }

        task.disable();

        return true;
    }
}
