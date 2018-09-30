package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import com.wisehollow.fundamentals.tasks.TeleportTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/13/2016.
 */
public class CommandTPAccept implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.TPAccept")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (!CommandTPA.tpaHash.containsValue(player)) {
            player.sendMessage(Language.getInstance().teleportRequestNone);
            return true;
        }

        List<Player> requested = new ArrayList<>();

        for (Player p : CommandTPA.tpaHash.keySet()) {
            if (CommandTPA.tpaHash.get(p).equals(player)) {
                requested.add(p);
            }
        }

        for (Player p : requested) {
            Bukkit.getServer().getScheduler().cancelTask(CommandTPA.tpaTaskIDs.get(p));
            CommandTPA.tpaTaskIDs.remove(p);

            p.sendMessage(Language.getInstance().teleportRequestAccept);
            TeleportTask task = new TeleportTask(p, player.getLocation().clone(), Settings.TeleportDelay);
            task.run();
        }

        return true;
    }
}
