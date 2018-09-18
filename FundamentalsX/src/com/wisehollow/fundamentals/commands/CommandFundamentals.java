package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by John on 10/13/2016.
 */
public class CommandFundamentals implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        if (args[0].equalsIgnoreCase("reload")) {
            Settings.load();
            Settings.loadMotd();
            sender.sendMessage(Language.ConfigurationsReloaded);
            return true;
        } else if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(Language.PluginVersion + Main.getPlugin().getDescription().getVersion());
            return true;
        }

        return false;
    }
}
