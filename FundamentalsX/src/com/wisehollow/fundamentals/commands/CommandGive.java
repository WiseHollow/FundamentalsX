package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.MaterialIndex;
import com.wisehollow.fundamentals.utils.PlayerUtil;
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
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length > 1) {
            Player target = PlayerUtil.GetPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(Language.getInstance().notLoggedIn);
                return true;
            }

            String[] materialData = args[1].toUpperCase().split(":");

            Material given = MaterialIndex.getMaterial(materialData[0]);
            if (given == null) {
                sender.sendMessage(Language.getInstance().materialInvalid);
                return true;
            }
            int amount = 64;
            if (args.length == 3) {
                try {
                    amount = Integer.valueOf(args[2]);
                } catch (Exception ex) {
                    sender.sendMessage(Language.getInstance().mustBeANumber);
                    return true;
                }
            }

            target.getInventory().addItem(new ItemStack(given, amount));
            target.sendMessage(Language.getInstance().receivedItemStack
                    .replace("%a", Integer.toString(amount))
                    .replace("%m", given.name()));

        } else {
            return false;
        }

        return true;
    }
}
