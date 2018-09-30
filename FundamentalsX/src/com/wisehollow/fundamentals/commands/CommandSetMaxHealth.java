package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandSetMaxHealth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.SetMaxHealth")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;

        double health;
        try {
            health = Double.valueOf(args[0]);
        } catch (Exception ex) {
            return false;
        }

        if (args.length > 1 && sender.hasPermission("Fundamentals.SetMaxHealth.Other")) {
            player = PlayerUtil.GetPlayer(args[1]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }

            sender.sendMessage(Language.getInstance().maxHealthOfPlayerSet
                    .replace("%p", player.getName())
                    .replace("%m", Double.toString(health)));
            player.sendMessage(Language.getInstance().maxHealthSet.replace("%m", Double.toString(health)));
        } else {
            sender.sendMessage(Language.getInstance().maxHealthSet.replace("%m", Double.toString(health)));
        }

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        player.setHealth(health);

        return true;
    }
}
