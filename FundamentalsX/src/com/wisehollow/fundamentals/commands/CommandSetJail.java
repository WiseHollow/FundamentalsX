package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandSetJail implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Jail.Manage")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length != 1)
            return false;
        if (Settings.jails.containsKey(args[0]))
            player.sendMessage(Language.getInstance().jailUpdated);
        else
            player.sendMessage(Language.getInstance().jailCreated);
        Settings.jails.put(args[0], player.getLocation().clone());
        Settings.Save();

        return true;
    }
}
