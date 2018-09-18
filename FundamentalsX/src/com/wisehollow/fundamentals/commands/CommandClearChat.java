package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandClearChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.ClearChat")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < 100; i++)
                p.sendMessage(" ");
        }

        return true;
    }
}
