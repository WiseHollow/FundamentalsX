package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

/**
 * Created by John on 10/13/2016.
 */
public class CommandBan implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Kick")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;

        StringBuilder reasonStringBuilder = new StringBuilder();
        if (args.length == 1) {
            reasonStringBuilder = new StringBuilder("Banned from server.");
        } else {
            for (int i = 1; i < args.length; i++)
                reasonStringBuilder.append(ChatColor.translateAlternateColorCodes('&', args[i])).append(" ");
        }

        final String reason = reasonStringBuilder.toString();

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (target == null || !target.isOnline()) {

            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[0]);
                if (target == null || !target.hasPlayedBefore()) {
                    sender.sendMessage(Language.getInstance().targetNotOnline);
                } else {
                    ban(sender, offlineTarget, reason);
                }
            });

            return true;
        }

        //Variables:
        //- username: the username or IP-address if you are ip-banning (String)
        //- reason: String: the reason. (You can use ChatColor)
        //- expires: This is the Data (java.util.Data) insert 'null' to permanent ban
        //- source: The player's name who banned. Not really important. (Could also be 'console' or something like that)

        ban(sender, target, reason);
        return true;
    }

    private void ban(final CommandSender sender, final OfflinePlayer target, final String reason) {
        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, sender.getName());
        if (target.isOnline()) {
            ((Player) target).kickPlayer(reason);
        }

        sender.sendMessage(Language.getInstance().banPlayer.replace("%p", target.getName()).replace("%r", reason));
    }
}
