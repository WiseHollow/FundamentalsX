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
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        if (!sender.hasPermission("Fundamentals.TPToggle")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = PlayerData.GetPlayerData(player);

        if (playerData != null) {
            playerData.setTeleportDisabled(!playerData.hasTeleportDisabled());
            sender.sendMessage(Language.TeleportHasBeenToggled + (playerData.hasTeleportDisabled() ? "off." : "on."));
        }

        return true;
    }

}
