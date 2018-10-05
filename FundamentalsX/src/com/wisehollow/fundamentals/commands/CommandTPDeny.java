package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.userdata.TeleportationRequest;
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
public class CommandTPDeny implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        final PlayerData playerData = PlayerData.getPlayerData(player);
        if (!sender.hasPermission("Fundamentals.TPDeny")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (!playerData.getTeleportationRequest().isPresent()) {
            player.sendMessage(Language.getInstance().teleportRequestNone);
            return true;
        }

        TeleportationRequest request = playerData.getTeleportationRequest().get();
        request.deny();
        return true;
    }
}
