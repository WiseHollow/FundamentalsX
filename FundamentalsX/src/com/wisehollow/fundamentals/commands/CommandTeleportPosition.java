package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import com.wisehollow.fundamentals.tasks.TeleportTask;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandTeleportPosition implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.TPPOS")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length != 3) {
            return false;
        }

        int[] positions = new int[3];

        for (int i = 0; i < 3; i++) {
            try {
                positions[i] = Integer.valueOf(args[i]);
            } catch (Exception ex) {
                player.sendMessage(Language.PREFIX_WARNING + "Invalid position to teleport to.");
                return true;
            }
        }

        Location target = new Location(player.getWorld(), positions[0], positions[1], positions[2]);

        TeleportTask task = new TeleportTask(player, target, Settings.TeleportDelay);
        task.run();

        return true;
    }
}
