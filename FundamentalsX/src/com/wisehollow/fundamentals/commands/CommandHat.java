package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {
        if (!sender.hasPermission("Fundamentals.Hat")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (player.getInventory().getItemInMainHand() == null
                || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            sender.sendMessage(Language.getInstance().hatSetError);
            return true;
        }

        ItemStack inHand = player.getInventory().getItemInMainHand();
        ItemStack helmet = player.getInventory().getHelmet();

        if (helmet != null && helmet.getType() != Material.AIR) {
            player.getInventory().setItemInMainHand(helmet);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR, 1));
        }

        player.getInventory().setHelmet(inHand);
        player.sendMessage(Language.getInstance().hatSet);

        return true;
    }
}