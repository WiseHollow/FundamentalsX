package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.MaterialIndex;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by John on 10/13/2016.
 */
public class CommandI implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }
        Player player = (Player) sender;

        if (!sender.hasPermission("Fundamentals.I")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 0)
            return false;


        String[] materialData = args[0].toUpperCase().split(":");
        Material given = MaterialIndex.getMaterial(materialData[0]);
        if (given == null) {
            sender.sendMessage(Language.getInstance().materialInvalid);
            return true;
        }
        int amount = 64;
        if (args.length >= 2) {
            try {
                amount = Integer.valueOf(args[1]);
            } catch (Exception ex) {
                sender.sendMessage(Language.getInstance().mustBeANumber);
                return true;
            }
        }

        player.getInventory().addItem(new ItemStack(given, amount));
        player.sendMessage(Language.getInstance().receivedItemStack
                .replace("%a", Integer.toString(amount))
                .replace("%m", given.name()));

        return true;
    }
}
