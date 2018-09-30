package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import com.wisehollow.fundamentals.tasks.GodTask;
import com.wisehollow.fundamentals.tasks.NukeTask;
import com.wisehollow.fundamentals.tasks.VanishTask;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/13/2016.
 */
public class CommandNuke implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Nuke")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        Player target = PlayerUtil.GetPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(Language.getInstance().targetNotOnline);
            return true;
        }

        if (target.getGameMode() != GameMode.ADVENTURE && target.getGameMode() != GameMode.SURVIVAL) {
            player.sendMessage(Language.getInstance().playerMustBeInGameMode);
            return true;
        }

        if (GodTask.GetTask(target) != null) {
            player.sendMessage(Language.getInstance().playerCannotBeInGodMode);
            return true;
        }

        if (VanishTask.GetTask(target) != null) {
            player.sendMessage(Language.getInstance().playerCannotBeVanished);
            return true;
        }

        NukeTask task = NukeTask.GetTask(target);
        if (task == null) {
            task = new NukeTask(target);
            task.run();
            player.sendMessage(Language.getInstance().nukeTaskStart.replace("%p", target.getName()));
        } else {
            task.disable();
            player.sendMessage(Language.getInstance().nukeTaskStop.replace("%p", target.getName()));
        }

        return true;
    }
}
