package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import com.wisehollow.fundamentals.tasks.TeleportTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Home")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        PlayerData pd = PlayerData.getPlayerData(player);
        String name;

        if (pd.getHomes().size() == 0) {
            player.sendMessage(Language.PREFIX_WARNING + "You do not have any homes yet.");
            return true;
        } else if (args.length == 0 && pd.getHomes().size() > 1) {
            StringBuilder homes = new StringBuilder();
            for (String s : pd.getHomes().keySet()) {
                homes.append(s).append(" ");
            }
            player.sendMessage(Language.PREFIX + ChatColor.BOLD + "Homes: " + ChatColor.RESET + homes);
            return true;
        } else if (args.length == 0 && pd.getHomes().size() == 1) {
            name = pd.getHomes().keySet().stream().findFirst().get();
        } else {
            name = args[0];
        }

        Location target = pd.getHome(name);

        if (target == null) {
            player.sendMessage(Language.PREFIX_WARNING + "Home does not exist!");
            return true;
        }

        TeleportTask task = new TeleportTask(player, target, Settings.TeleportDelay);
        task.run();

        return true;
    }
}
