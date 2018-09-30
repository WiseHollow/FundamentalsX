package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.tasks.SocialSpyTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandSocialSpy implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.SocialSpy")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        SocialSpyTask task = SocialSpyTask.getSocialSpyTask(player);
        if (task != null) {
            task.disable();
        } else {
            task = new SocialSpyTask(player);
            task.run();
        }

        return true;
    }
}
