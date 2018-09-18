package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.PlayerUtil;
import com.wisehollow.fundamentals.Settings;
import com.wisehollow.fundamentals.tasks.JailTask;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandJail implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Jail")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length <= 1)
            return false;

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(Language.PlayerMustBeLoggedIn);
            return true;
        }
        Location jail;
        if (Settings.jails.containsKey(args[1]))
            jail = Settings.jails.get(args[1]);
        else {
            sender.sendMessage(Language.PREFIX_WARNING + "That jail does not exist!");
            return true;
        }

        int seconds = 0;

        if (args.length == 3) {
            String time = args[2];
            char c = time.toLowerCase().charAt(time.length() - 1);
            try {
                seconds = Integer.valueOf(time.split(String.valueOf(c))[0]);
            } catch (Exception ex) {
                sender.sendMessage(Language.PREFIX_WARNING + "Invalid formatting. (Time: 50m, 15s, 1h)");
                return true;
            }

            if (c == 'm')
                seconds *= 60;
            else if (c == 'h')
                seconds *= 60 * 60;
            else if (c == 'd')
                seconds *= 60 * 60 * 24;
            else {
                sender.sendMessage(Language.PREFIX_WARNING + "Invalid formatting. (Format: 50m, 15s, 1h)");
                return true;
            }
        }

        JailTask task = new JailTask(target, jail, seconds);
        if (!task.run())
            sender.sendMessage(Language.PREFIX_WARNING + "You cannot jail that player!");
        else
            sender.sendMessage(Language.PREFIX_WARNING + "You have jailed: " + target.getName());

        return true;
    }
}
