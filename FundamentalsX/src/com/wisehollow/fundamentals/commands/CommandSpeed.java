package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandSpeed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Speed")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;

        if (args.length > 1) {
            player = PlayerUtil.GetPlayer(args[1]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }
        }

        float speed;
        try {
            speed = (float) Integer.valueOf(args[0]) / 10;
        } catch (Exception ex) {
            return false;
        }

        Integer speedQuote = ((int) (speed * 10));
        if (speedQuote > 10 || speedQuote < 1) {
            sender.sendMessage(Language.getInstance().invalidSpeed);
            return true;
        }

        if (player.isFlying()) {
            player.setFlySpeed(speed);
            sender.sendMessage(Language.getInstance().flySpeedSet.replace("%s", Integer.toString(speedQuote)));
            if (!sender.equals(player))
                player.sendMessage(Language.getInstance().flySpeedSet.replace("%s", Integer.toString(speedQuote)));
        } else {
            player.setWalkSpeed(speed);
            sender.sendMessage(Language.getInstance().walkSpeedSet.replace("%s", Integer.toString(speedQuote)));
            if (!sender.equals(player))
                player.sendMessage(Language.getInstance().walkSpeedSet.replace("%s", Integer.toString(speedQuote)));
        }

        return true;
    }
}
