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
public class CommandDelJail implements CommandExecutor {
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

        if (args.length != 1) {
            return false;
        } else if (!Settings.jails.containsKey(args[0])) {
            player.sendMessage(Language.getInstance().jailDoesNotExist);
            return true;
        }

        Settings.jails.remove(args[0]);
        player.sendMessage(Language.getInstance().jailRemoved);
        Settings.Save();

        return true;
    }
}
