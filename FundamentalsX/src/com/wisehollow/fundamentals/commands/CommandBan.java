package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandBan implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Kick")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(Language.PlayerMustBeLoggedIn);
            return true;
        }

        String reason = "";
        if (args.length == 1) {
            reason = "Banned from server.";
        } else {
            for (int i = 1; i < args.length; i++)
                reason += ChatColor.translateAlternateColorCodes('&', args[i]) + " ";
        }

        //Variables:
        //- username: the username or IP-address if you are ip-banning (String)
        //- reason: String: the reason. (You can use ChatColor)
        //- expires: This is the Data (java.util.Data) insert 'null' to permanent ban
        //- source: The player's name who banned. Not really important. (Could also be 'console' or something like that)

        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, sender.getName());
        target.kickPlayer(reason);

        sender.sendMessage(Language.PREFIX + "You have banned " + target.getName() + " for reason: " + reason);

        return true;
    }
}
