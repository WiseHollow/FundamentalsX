package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
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
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;

        List<Player> targets = new ArrayList<>();

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (args[0].equalsIgnoreCase("all")) {
            targets.addAll(Bukkit.getOnlinePlayers());
        } else if (target == null || !target.isOnline()) {
            sender.sendMessage(Language.getInstance().targetNotOnline);
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
            sender.sendMessage(Language.getInstance().kickAll.replace("%r", reason));
        else if (target != null)
            sender.sendMessage(Language.getInstance().kickPlayer
                    .replace("%p", target.getName())
                    .replace("%r", reason));

        return true;
    }
}
