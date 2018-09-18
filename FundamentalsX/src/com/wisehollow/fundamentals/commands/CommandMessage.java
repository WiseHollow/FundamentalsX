package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.customevents.SendPrivateMessageEvent;
import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandMessage implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Message")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length < 2)
            return false;

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(Language.PREFIX + "That player is not online.");
            return true;
        }

        String msg = "";
        for (int i = 1; i < args.length; i++)
            msg += args[i] + " ";

        //String sPrefix = "";
        //if (sender instanceof Player)
        //    sPrefix = PlayerUtil.GetPlayerPrefix((Player) sender);
        //String rPrefix = PlayerUtil.GetPlayerPrefix(target);

        //sender.sendMessage(sPrefix + sender.getName() + " -> " + ChatColor.BOLD + rPrefix + target.getName() + ChatColor.RESET + " | " + msg);
        //target.sendMessage(ChatColor.BOLD + sPrefix + sender.getName() + ChatColor.RESET + " -> " + rPrefix + target.getName() + " | " + msg);

        //sender.sendMessage(" -> " + ChatColor.BOLD + rPrefix + target.getName() + ChatColor.RESET + " | " + msg);
        //target.sendMessage(ChatColor.BOLD + sPrefix + sender.getName() + ChatColor.RESET + " -> " + rPrefix + target.getName() + " | " + msg);

        SendPrivateMessageEvent pm = new SendPrivateMessageEvent(sender, target, msg);
        Bukkit.getPluginManager().callEvent(pm);
        pm.run();

        CommandReply.senderAndReceivers.put(target, sender);

        return true;
    }
}
