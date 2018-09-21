package com.wisehollow.fundamentalscombat.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentalscombat.tasks.PVPTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPVP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        } else if (!sender.hasPermission("Fundamentals.EnablePvP")) {
            sender.hasPermission(Language.DoesNotHavePermission);
            return true;
        }

        boolean result = PVPTimer.disableTimer((Player) sender);
        if (result) {
            sender.sendMessage(Language.PVPTimerDisabled);
        } else {
            sender.sendMessage(Language.PlayerDoesNotHavePVPTimer);
        }

        return true;
    }
}
