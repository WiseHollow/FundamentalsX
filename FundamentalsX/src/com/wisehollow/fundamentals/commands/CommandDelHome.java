package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandDelHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.DelHome")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        String name = args[0];
        PlayerData pd = PlayerData.GetPlayerData(player);

        if (!pd.deleteHome(name)) {
            player.sendMessage(Language.PREFIX_WARNING + "Home does not exist!");
            return true;
        }

        player.sendMessage(Language.PREFIX + "Home has been removed!");
        return true;
    }
}
