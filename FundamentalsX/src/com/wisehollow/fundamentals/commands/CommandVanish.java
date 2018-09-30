package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.tasks.VanishTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandVanish implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Vanish")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        VanishTask task = VanishTask.GetTask(player);

        if (task == null) {
            player.sendMessage(Language.getInstance().vanished);
            task = new VanishTask(player);
            task.run();
        } else {
            player.sendMessage(Language.getInstance().noLongerVanished);
            task.disable();
        }

        return true;
    }
}
