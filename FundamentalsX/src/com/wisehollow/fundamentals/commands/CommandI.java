package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.MaterialIndex;
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
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }
        Player player = (Player) sender;

        if (!sender.hasPermission("Fundamentals.I")) {
            sender.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;


        String[] materialData = args[0].toUpperCase().split(":");
        Material given = MaterialIndex.getMaterial(materialData[0]);
        if (given == null) {
            sender.sendMessage(Language.PREFIX_WARNING + "Invalid material given.");
            return true;
        }
        int amount = 64;
        if (args.length >= 2) {
            try {
                amount = Integer.valueOf(args[1]);
            } catch (Exception ex) {
                sender.sendMessage(Language.PREFIX_WARNING + "Invalid amount to give.");
                return true;
            }
        }

        player.getInventory().addItem(new ItemStack(given, amount));
        player.sendMessage(Language.PREFIX + "You were given x" + amount + " of " + given.name());

        return true;
    }
}
