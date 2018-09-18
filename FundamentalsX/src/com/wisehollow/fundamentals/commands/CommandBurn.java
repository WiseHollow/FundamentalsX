package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/13/2016.
 */
public class CommandBurn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Burn")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        List<Player> targets = new ArrayList<>();

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (target != null) {
            targets.add(target);
        } else if (args[0].equalsIgnoreCase("all")) {
            targets.addAll(Bukkit.getOnlinePlayers());
        } else {
            sender.sendMessage(Language.PlayerMustBeLoggedIn);
            return true;
        }

        int seconds;
        try {
            seconds = Integer.valueOf(args[1]);
        } catch (Exception ex) {
            sender.sendMessage(Language.PREFIX_WARNING + "Invalid time input.");
            return true;
        }

        for (Player p : targets) {
            if (!p.equals(sender)) {
                p.setFireTicks(20 * seconds);
            }
        }

        if (args[0].equalsIgnoreCase("all"))
            sender.sendMessage(Language.PREFIX + "You have burned all players for " + seconds + " seconds");
        else
            sender.sendMessage(Language.PREFIX + "You have burned " + target.getName() + " for " + seconds + " seconds");

        return true;
    }
}
