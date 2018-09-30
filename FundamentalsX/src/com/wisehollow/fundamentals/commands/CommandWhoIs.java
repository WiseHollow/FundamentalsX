package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import com.wisehollow.fundamentals.tasks.AFKTask;
import com.wisehollow.fundamentals.tasks.GodTask;
import com.wisehollow.fundamentals.tasks.JailTask;
import com.wisehollow.fundamentals.tasks.VanishTask;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandWhoIs implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.WhoIs")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(Language.getInstance().targetNotOnline);
            return true;
        }

        sender.sendMessage(Language.getInstance().playerInfo + target.getName());
        sender.sendMessage(Language.getInstance().health + target.getHealth() + "/" + target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        sender.sendMessage(Language.getInstance().food + target.getFoodLevel() + "/" + "20");
        sender.sendMessage(Language.getInstance().walkSpeed + target.getWalkSpeed());
        sender.sendMessage(Language.getInstance().flySpeed + target.getFlySpeed());
        sender.sendMessage(Language.getInstance().gameMode + target.getGameMode().name());
        sender.sendMessage(Language.getInstance().location + target.getWorld().getName() + "   " + target.getLocation().getBlockX() + " " + target.getLocation().getBlockY() + " " + target.getLocation().getBlockZ());
        sender.sendMessage(Language.getInstance().godMode + ((GodTask.GetTask(target) == null) ? "false" : "true"));
        sender.sendMessage(Language.getInstance().afk + ((AFKTask.GetTask(target) == null) ? "false" : "true"));
        sender.sendMessage(Language.getInstance().jail + ((JailTask.GetTask(target) == null) ? "false" : "true"));
        sender.sendMessage(Language.getInstance().vanish + ((VanishTask.GetTask(target) == null) ? "false" : "true"));

        return true;
    }
}
