package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by John on 10/13/2016.
 */
public class CommandFundamentals implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;

        if (args[0].equalsIgnoreCase("reload")) {
            Settings.load();
            Settings.loadMotd();
            sender.sendMessage(Language.getInstance().configurationReloaded);
            return true;
        } else if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(Language.getInstance().pluginVersion.replace("%v", Main.getPlugin().getDescription().getVersion()));
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String[] tabs = new String[] { "version", "reload" };
        return Arrays.stream(tabs).collect(Collectors.toList());
    }
}
