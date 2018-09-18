package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by John on 10/13/2016.
 */
public class CommandSmite implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Smite")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (!(sender instanceof Player) && args.length != 0) {
            //TODO: Handle as server console.

            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Location target;

        if (sender instanceof Player && args.length == 0) {
            Set<Material> set = null;
            target = ((Player) sender).getTargetBlock(set, 150).getLocation();
        } else if (args.length > 0) {
            Player p = PlayerUtil.GetPlayer(args[0]);
            if (p == null || !p.isOnline()) {
                sender.sendMessage(Language.PlayerMustBeLoggedIn);
                return true;
            }
            p.sendMessage(Language.PREFIX + "Thou have been smitten.");
            target = p.getLocation();
        } else {
            return true;
        }

        target.getWorld().strikeLightning(target);

        return true;
    }
}
