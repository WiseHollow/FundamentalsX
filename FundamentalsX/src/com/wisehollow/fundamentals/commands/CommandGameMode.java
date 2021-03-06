package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by John on 10/13/2016.
 */
public class CommandGameMode implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.GameMode")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;

        if (args.length > 1 && sender.hasPermission("Fundamentals.GameMode.Other")) {
            player = PlayerUtil.GetPlayer(args[1]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }
        }

        String gm = args[0];
        GameMode mode = null;
        if (gm.equalsIgnoreCase("0") || gm.equalsIgnoreCase("Survival") || gm.equalsIgnoreCase("s")) {
            if (!sender.hasPermission("Fundamentals.GameModes.Survival")) {
                sender.sendMessage(Language.getInstance().unauthorized);
                return true;
            }
            mode = GameMode.SURVIVAL;
        } else if (gm.equalsIgnoreCase("1") || gm.equalsIgnoreCase("Creative") || gm.equalsIgnoreCase("c")) {
            if (!sender.hasPermission("Fundamentals.GameModes.Creative")) {
                sender.sendMessage(Language.getInstance().unauthorized);
                return true;
            }
            mode = GameMode.CREATIVE;
        } else if (gm.equalsIgnoreCase("2") || gm.equalsIgnoreCase("Adventure") || gm.equalsIgnoreCase("a")) {
            if (!sender.hasPermission("Fundamentals.GameModes.Adventure")) {
                sender.sendMessage(Language.getInstance().unauthorized);
                return true;
            }
            mode = GameMode.ADVENTURE;
        } else if (gm.equalsIgnoreCase("3") || gm.equalsIgnoreCase("Spectator") || gm.equalsIgnoreCase("sp")) {
            if (!sender.hasPermission("Fundamentals.GameModes.Spectator")) {
                sender.sendMessage(Language.getInstance().unauthorized);
                return true;
            }
            mode = GameMode.SPECTATOR;
        }

        if (mode != null) {
            player.setGameMode(mode);
            player.sendMessage(Language.getInstance().gameModeSet.replace("%g", mode.name()));
            if (args.length > 1)
                sender.sendMessage(Language.getInstance().gameModeOfPlayerSet
                        .replace("%p", player.getName())
                        .replace("%g", mode.name()));
        } else {
            player.sendMessage(Language.getInstance().gameModeInvalid);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String[] tabs = new String[] { "survival", "creative", "adventure", "spectator" };
        return Arrays.stream(tabs).collect(Collectors.toList());
    }
}
