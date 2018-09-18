package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.PlayerUtil;
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
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Speed")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        if (args.length > 1) {
            player = PlayerUtil.GetPlayer(args[1]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.PlayerMustBeLoggedIn);
                return true;
            }
        }

        float speed;
        try {
            speed = (float) Integer.valueOf(args[0]) / 10;
        } catch (Exception ex) {
            return false;
        }

        if (speed > 1 || speed <= 0) {
            sender.sendMessage(Language.PREFIX_WARNING + "Speed must be a number between 1-10");
            return true;
        }

        if (player.isFlying()) {
            player.setFlySpeed(speed);
            sender.sendMessage(Language.PREFIX + "Fly speed set to: " + (int) (speed * 10));
            if (!sender.equals(player))
                player.sendMessage(Language.PREFIX + "Fly speed set to: " + (int) (speed * 10));
        } else {
            player.setWalkSpeed(speed);
            sender.sendMessage(Language.PREFIX + "Walk speed set to: " + (int) (speed * 10));
            if (!sender.equals(player))
                player.sendMessage(Language.PREFIX + "Walk speed set to: " + (int) (speed * 10));
        }

        return true;
    }
}
