package com.wisehollow.fundamentalschat.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import com.wisehollow.fundamentalschat.tasks.MuteTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Created by John on 10/20/2016.
 */
public class CommandMute implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!sender.hasPermission("Fundamentals.Mute")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length > 0) {
            Optional<MuteTask> optionalMuteTask = MuteTask.getOfflineTask(args[0]);
            if (optionalMuteTask.isPresent()) {
                MuteTask muteTask = optionalMuteTask.get();
                muteTask.Disable();
            } else {
                Player target = PlayerUtil.GetPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Language.getInstance().targetNotOnline);
                } else {
                    MuteTask.insertTask(target);
                    sender.sendMessage(Language.getInstance().mutePlayer.replace("%p", target.getName()));
                }
            }

            return true;
        }


        return false;
    }
}
