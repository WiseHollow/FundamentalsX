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
public class CommandSetSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.SetSpawn")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length > 0 && (args[0].equalsIgnoreCase("FirstJoin") || args[0].equalsIgnoreCase("FS"))) {
            Settings.SpawnFirstJoin = player.getLocation().clone();
            Settings.Save();

            player.sendMessage(Language.getInstance().firstJoinSet);
        } else {
            Settings.Spawn = player.getLocation().clone();
            Settings.Save();

            player.sendMessage(Language.getInstance().spawnSet);
        }
        return true;
    }
}
