package com.wisehollow.fundamentalseconomy.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.permissions.PermissionCheck;
import com.wisehollow.fundamentals.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBalance implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Balance"))
        {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length > 0 && PermissionCheck.HasModeratorPermissions(sender))
        {
            Player target = PlayerUtil.GetPlayer(args[0]);
            if (target == null || !target.isOnline())
            {
                player.sendMessage(Language.YouMustBeLoggedIn);
                return true;
            }

            sender.sendMessage(Language.PREFIX + target.getName() + "'s balance: " + Main.getPlugin().getEconomy().getBalance(target));
        }
        else
        {
            sender.sendMessage(Language.PREFIX + "Your balance: " + Main.getPlugin().getEconomy().getBalance(player));
        }

        return true;
    }
}
