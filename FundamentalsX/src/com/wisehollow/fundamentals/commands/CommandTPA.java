package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.userdata.PlayerData;
import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
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
    public static HashMap<Player, Player> tpaHash = new HashMap<>();
    public static HashMap<Player, Integer> tpaTaskIDs = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.TPA")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        Player target = PlayerUtil.GetPlayer(args[0]);
        PlayerData targetData = PlayerData.getPlayerData(target);

        if (target == null || !target.isOnline()) {
            player.sendMessage(Language.getInstance().targetNotOnline);
            return true;
        } else if (targetData != null && targetData.hasTeleportDisabled()) {
            player.sendMessage(Language.getInstance().cannotTeleportToPlayer);
            return true;
        }

        tpaHash.put(player, target);
        player.sendMessage(Language.getInstance().teleportRequestSent);
        target.sendMessage(Language.getInstance().teleportRequestReceived);

        tpaTaskIDs.put(player, Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
        {
            if (tpaHash.containsKey(player) && tpaHash.get(player).equals(target)) {
                tpaHash.remove(player);
            }
        }, 20 * 60 * 2));

        return true;
    }
}
