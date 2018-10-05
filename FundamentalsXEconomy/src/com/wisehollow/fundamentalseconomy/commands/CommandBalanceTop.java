package com.wisehollow.fundamentalseconomy.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.permissions.PermissionCheck;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import com.wisehollow.fundamentalseconomy.tasks.BalanceTopTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBalanceTop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Balance.Top")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        BalanceTopTask balanceTopTask = new BalanceTopTask(sender);
        balanceTopTask.start();
        return true;
    }
}
