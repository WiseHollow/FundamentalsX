package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Created by John on 10/13/2016.
 */
public class CommandEnchant implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Enchant")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (player.getInventory().getItemInMainHand() == null) {
            player.sendMessage(Language.PREFIX_WARNING + "You must have an item in your hand. ");
            return true;
        }

        if (args.length == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.asList(Enchantment.values()).forEach(enchantment -> stringBuilder.append(enchantment.getKey().getKey()).append(" "));
            player.sendMessage(Language.PREFIX + stringBuilder.toString());
            return true;
        }

        Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(args[0].toLowerCase()));

        int level = 1;
        if (args.length > 1) {
            try {
                level = Integer.valueOf(args[1]);
            } catch (NumberFormatException exception) {
                player.sendMessage(Language.PREFIX_WARNING + "Invalid number given!");
                return true;
            }
        }

        if (enchantment == null) {
            player.sendMessage(Language.PREFIX_WARNING + "Invalid enchantment type!");
            return true;
        }

        if (!Settings.AllowUnsafeEnchantments) {
            if (level > enchantment.getMaxLevel()) {
                player.sendMessage(Language.PREFIX_WARNING + "The max level for that enchantment is: " + enchantment.getMaxLevel());
                return true;
            } else {
                player.getInventory().getItemInMainHand().addEnchantment(enchantment, level);
            }
        } else {
            player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, level);
        }

        player.sendMessage(Language.PREFIX + "Enchantment: " + enchantment.getKey().getKey() + " added at level " + level);
        return true;
    }
}
