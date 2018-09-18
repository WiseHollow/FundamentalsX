package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by John on 10/13/2016.
 */
public class CommandButcher implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Butcher")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        boolean all = false;
        EntityType entityType = null;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("ALL")) {
                all = true;
            } else {
                entityType = EntityType.valueOf(args[0].toUpperCase());
            }
        }

        AtomicInteger kill = new AtomicInteger();
        EntityType finalEntityType = entityType;
        boolean finalAll = all;
        player.getWorld().getLivingEntities().forEach(livingEntity -> {
            if (livingEntity instanceof Monster || finalAll || (finalEntityType != null && livingEntity.getType() == finalEntityType)) {
                if (!(livingEntity instanceof Player)) {
                    livingEntity.remove();
                    kill.getAndIncrement();
                }
            }
        });
        player.sendMessage(Language.PREFIX + "Killed " + kill.get() + " entities.");

        return true;
    }
}
