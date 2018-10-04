package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.userdata.TeleportationRequest;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by John on 10/13/2016.
 */
public class CommandTPA implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        final PlayerData playerData = PlayerData.getPlayerData(player);
        if (!sender.hasPermission("Fundamentals.TPA")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        Player target = PlayerUtil.GetPlayer(args[0]);
        final PlayerData targetData = PlayerData.getPlayerData(target);
        if (target == null || !target.isOnline()) {
            player.sendMessage(Language.getInstance().targetNotOnline);
            return true;
        } else if (targetData != null && targetData.hasTeleportDisabled()) {
            player.sendMessage(Language.getInstance().cannotTeleportToPlayer);
            return true;
        }

        if (targetData != null) {
            TeleportationRequest request = new TeleportationRequest(player, target, playerData, targetData);
            request.initialize();
            targetData.setTeleportationRequest(request);
        }

        return true;
    }
}
