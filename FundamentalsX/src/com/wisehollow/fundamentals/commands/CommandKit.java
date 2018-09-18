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
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Kit")) {
            player.sendMessage(Language.DoesNotHavePermission);
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
            player.sendMessage(Language.PREFIX_WARNING + "That kit does not exist!");
            return true;
        }

        KitTask task = KitTask.GetTask(player);
        if (task == null) {
            task = new KitTask(player, kit);
            task.run();
        } else {
            if (task.getSecondsLeft() > 60)
                player.sendMessage(Language.PREFIX_WARNING + "Try using the kit again in another " + task.getSecondsLeft() / 60 + " minutes.");
            else
                player.sendMessage(Language.PREFIX_WARNING + "Try using the kit again in another " + task.getSecondsLeft() + " seconds.");
            return true;
        }

        return true;
    }
}
