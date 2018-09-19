package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import com.wisehollow.fundamentals.Settings;
import com.wisehollow.fundamentals.tasks.TeleportTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandTeleport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.TP")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        if (args.length == 1) {
            Player target = PlayerUtil.GetPlayer(args[0]);
            PlayerData targetData = PlayerData.getPlayerData(target);

            if (target == null || !target.isOnline()) {
                player.sendMessage(Language.PlayerMustBeLoggedIn);
                return true;
            } else if (targetData != null && targetData.hasTeleportDisabled()) {
                player.sendMessage(Language.PREFIX + Language.HasTeleportDisabled);
                return true;
            }

            TeleportTask task = new TeleportTask(player, target.getLocation().clone(), Settings.TeleportDelay);
            task.run();

            return true;
        } else if (args.length == 2 && sender.hasPermission("Fundamentals.TP.Other")) {
            Player source = PlayerUtil.GetPlayer(args[0]);
            Player target = PlayerUtil.GetPlayer(args[1]);
            PlayerData targetData = PlayerData.getPlayerData(target);

            if (target == null || !target.isOnline()) {
                player.sendMessage(Language.PlayerMustBeLoggedIn);
                return true;
            } else if (source == null || !source.isOnline()) {
                player.sendMessage(Language.PREFIX + Language.PlayerMustBeLoggedIn);
                return true;
            } else if (targetData != null && targetData.hasTeleportDisabled()) {
                player.sendMessage(Language.PREFIX + Language.HasTeleportDisabled);
                return true;
            }

            TeleportTask task = new TeleportTask(source, target.getLocation().clone(), 0);
            task.run();

            return true;
        }

        return false;
    }
}
