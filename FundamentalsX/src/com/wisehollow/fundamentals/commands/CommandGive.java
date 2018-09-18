package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.MaterialIndex;
import com.wisehollow.fundamentals.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by John on 10/13/2016.
 */
public class CommandGive implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.hasPermission("Fundamentals.Give")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length > 1) {
            Player target = PlayerUtil.GetPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(Language.PlayerMustBeLoggedIn);
                return true;
            }

            String[] materialData = args[1].toUpperCase().split(":");

            Material given = MaterialIndex.getMaterial(materialData[0]);
            if (given == null) {
                sender.sendMessage(Language.PREFIX_WARNING + "Invalid material given.");
                return true;
            }
            int amount = 64;
            if (args.length == 3) {
                try {
                    amount = Integer.valueOf(args[2]);
                } catch (Exception ex) {
                    sender.sendMessage(Language.PREFIX_WARNING + "Invalid amount to give.");
                    return true;
                }
            }

            target.getInventory().addItem(new ItemStack(given, amount));
            target.sendMessage(Language.PREFIX + "You were given x" + amount + " of " + given.name());

        } else {
            return false;
        }

        return true;
    }
}
