package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import com.wisehollow.fundamentals.tasks.TeleportTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandBack implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Back")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (!TeleportTask.PreviousLocation.containsKey(player)) {
            player.sendMessage(Language.PREFIX_WARNING + "No previous location recorded. ");
            return true;
        }

        TeleportTask task = new TeleportTask(player, TeleportTask.PreviousLocation.get(player), Settings.TeleportDelay);
        task.run();

        return true;
    }
}
