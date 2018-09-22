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

        if (speed > 10 || speed < 1) {
            sender.sendMessage(Language.getInstance().invalidSpeed);
            return true;
        }

        //TODO: What???
        String speedQuote = Integer.toString((int) (speed * 10));

        if (player.isFlying()) {
            player.setFlySpeed(speed);
            sender.sendMessage(Language.getInstance().flySpeedSet.replace("%s", speedQuote));
            if (!sender.equals(player))
                player.sendMessage(Language.getInstance().flySpeedSet.replace("%s", speedQuote));
        } else {
            player.setWalkSpeed(speed);
            sender.sendMessage(Language.getInstance().walkSpeedSet.replace("%s", speedQuote));
            if (!sender.equals(player))
                player.sendMessage(Language.getInstance().walkSpeedSet.replace("%s", speedQuote));
        }

        return true;
    }
}
