package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Kit;
import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.tasks.KitTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Kit")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0) {
            String kits = "";
            for (Kit k : Kit.GetAvailableKits(player))
                kits += k + " ";

            player.sendMessage("Available Kits: " + kits);

            return true;
        }

        String name = args[0];
        Kit kit = Kit.GetKit(name);
        if (kit == null) {
            player.sendMessage(Language.getInstance().kitDoesNotExist);
            return true;
        }

        KitTask task = KitTask.GetTask(player);
        if (task == null) {
            task = new KitTask(player, kit);
            task.run();
        } else {
            if (task.getSecondsLeft() > 60)
                player.sendMessage(Language.getInstance().kitDelayMinutes
                        .replace("%t", Integer.toString(task.getSecondsLeft() / 60)));
            else
                player.sendMessage(Language.getInstance().kitDelaySeconds
                        .replace("%t", Integer.toString(task.getSecondsLeft())));
            return true;
        }

        return true;
    }
}
