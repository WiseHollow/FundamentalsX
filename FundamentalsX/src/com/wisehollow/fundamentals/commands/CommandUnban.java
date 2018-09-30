package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by John on 10/13/2016.
 */
public class CommandUnban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Kick")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;

        //Bukkit.getBanlist(Type.NAME).addBan(username, reason, expires, source);

        //Variables:
        //- username: the username or IP-address if you are ip-banning (String)
        //- reason: String: the reason. (You can use ChatColor)
        //- expires: This is the Data (java.util.Data) insert 'null' to permanent ban
        //- source: The player's name who banned. Not really important. (Could also be 'console' or something like that)

        if (!Bukkit.getBanList(BanList.Type.NAME).isBanned(args[0])) {
            sender.sendMessage(Language.getInstance().notBanned);
            return true;
        }

        Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);
        sender.sendMessage(Language.getInstance().unbanned.replace("%p", args[0]));
        return true;
    }
}
