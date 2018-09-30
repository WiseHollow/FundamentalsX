package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandKill implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Kill")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length > 0) {
            Player target = PlayerUtil.GetPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }

            target.setHealth(0);
            player.sendMessage(Language.getInstance().killPlayer.replace("%p", target.getName()));
            return true;
        }

        return false;
    }
}
