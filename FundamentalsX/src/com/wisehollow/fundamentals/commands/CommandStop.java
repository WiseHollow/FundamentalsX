package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.tasks.StopTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by John on 10/13/2016.
 */
public class CommandStop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Stop")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0) {
            Bukkit.getServer().shutdown();
            return true;
        } else {
            int minutes;
            if (args[0].equalsIgnoreCase("Cancel")) {
                if (StopTask.stopTask == null)
                    return true;
                StopTask.stopTask.disable();
                return true;
            }

            try {
                minutes = Integer.valueOf(args[0]);
            } catch (Exception ex) {
                sender.sendMessage(Language.getInstance().prefixWarning + ex.getMessage());
                return true;
            }

            StopTask task = new StopTask(minutes);
            if (!task.run()) {
                sender.sendMessage(Language.getInstance().shutdownTaskAlreadyStarted);
            }
        }

        return true;
    }
}
