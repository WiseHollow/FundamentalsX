package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by John on 10/13/2016.
 */
public class CommandListJail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Jail")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        String jails = "";
        for (String s : Settings.jails.keySet()) {
            jails += s + " ";
        }

        sender.sendMessage(Language.getInstance().jailList + jails);
        return true;
    }
}
