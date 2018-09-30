package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandSetHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.SetHome")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        PlayerData pd = PlayerData.getPlayerData(player);

        int amount = pd.getHomes().size();
        if (!Settings.HasPermissionForHomeAmount(player, amount + 1)) {
            player.sendMessage(Language.getInstance().homesMaxed);
            return true;
        }

        String name = args.length > 0 ? args[0] : "home";
        pd.setHome(name);

        player.sendMessage(Language.getInstance().homeSet.replace("%h", name));
        return true;
    }
}
