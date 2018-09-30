package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.runnables.CommandSeenRunnable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSeen implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("Fundamentals.Seen")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 1) {
            sendTargetPlayerInformation(sender, args[0]);
            return true;
        }

        return false;
    }

    private void sendTargetPlayerInformation(final CommandSender sender, final String desiredPlayer) {
        Thread thread = new Thread(new CommandSeenRunnable(sender, desiredPlayer));
        thread.start();
    }

}
