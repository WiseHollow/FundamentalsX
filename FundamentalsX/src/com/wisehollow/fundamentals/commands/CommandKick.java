package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/13/2016.
 */
public class CommandKick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Kick")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        List<Player> targets = new ArrayList<>();

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (args[0].equalsIgnoreCase("all")) {
            targets.addAll(Bukkit.getOnlinePlayers());
        } else if (target == null || !target.isOnline()) {
            sender.sendMessage(Language.PlayerMustBeLoggedIn);
            return true;
        }

        targets.add(target);

        String reason = "";
        if (args.length == 1) {
            reason = "Kicked from server.";
        } else {
            for (int i = 1; i < args.length; i++)
                reason += ChatColor.translateAlternateColorCodes('&', args[i]) + " ";
        }

        for (Player p : targets) {
            if (!p.equals(sender)) {
                p.kickPlayer(reason);
            }
        }

        if (args[0].equalsIgnoreCase("all"))
            sender.sendMessage(Language.PREFIX + "You have kicked all players for reason: " + reason);
        else if (sender != null)
            sender.sendMessage(Language.PREFIX + "You have kicked " + target.getName() + " for reason: " + reason);

        return true;
    }
}
