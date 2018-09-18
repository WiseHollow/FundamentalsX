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
public class CommandDelWarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.SetWarp")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        String name = args[0].toLowerCase();

        if (!Settings.warps.containsKey(name)) {
            player.sendMessage(Language.PREFIX_WARNING + "Warp does not exist!");
            return true;
        }

        Settings.warps.remove(name);

        player.sendMessage(Language.PREFIX + "Warp has been removed!");
        Settings.Save();
        return true;
    }
}
