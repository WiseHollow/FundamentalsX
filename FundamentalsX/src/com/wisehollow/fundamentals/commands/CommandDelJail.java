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
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Jail.Manage")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length != 1)
            return false;
        if (!Settings.jails.containsKey(args[0])) {
            player.sendMessage(Language.PREFIX_WARNING + "That jail does not exist.");
            return true;
        }

        Settings.jails.remove(args[0]);
        player.sendMessage(Language.PREFIX + "Jail has been deleted!");
        Settings.Save();

        return true;
    }
}
