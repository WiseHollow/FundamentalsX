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
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Kill")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length > 0) {
            Player target = PlayerUtil.GetPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(Language.PlayerMustBeLoggedIn);
                return true;
            }

            target.setHealth(0);
            player.sendMessage(Language.PREFIX_WARNING + "You have killed " + target.getName() + ".");
        } else {
            return false;
        }

        return true;
    }
}
