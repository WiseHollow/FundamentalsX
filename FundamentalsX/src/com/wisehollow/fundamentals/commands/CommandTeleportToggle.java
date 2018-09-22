package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTeleportToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        if (!sender.hasPermission("Fundamentals.TPToggle")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = PlayerData.getPlayerData(player);

        if (playerData != null) {
            playerData.setTeleportDisabled(!playerData.hasTeleportDisabled());
            String status = (playerData.hasTeleportDisabled() ? "off." : "on.");
            sender.sendMessage(Language.getInstance().teleportToggled
                    .replace("%s", status));
        }

        return true;
    }

}
