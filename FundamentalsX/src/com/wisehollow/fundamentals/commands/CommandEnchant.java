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
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Enchant")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (player.getInventory().getItemInMainHand() == null) {
            player.sendMessage(Language.getInstance().itemNotInHand);
            return true;
        }

        if (args.length == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.asList(Enchantment.values()).forEach(enchantment -> stringBuilder.append(enchantment.getName()).append(" "));
            player.sendMessage(Language.getInstance().prefixInfo + stringBuilder.toString());
            return true;
        }

        Enchantment enchantment = EnchantmentWrapper.getByName(args[0].toLowerCase());

        int level = 1;
        if (args.length > 1) {
            try {
                level = Integer.valueOf(args[1]);
            } catch (NumberFormatException exception) {
                player.sendMessage(Language.getInstance().mustBeANumber);
                return true;
            }
        }

        if (enchantment == null) {
            player.sendMessage(Language.getInstance().invalidEnchantment);
            return true;
        }

        if (!Settings.AllowUnsafeEnchantments) {
            if (level > enchantment.getMaxLevel()) {
                player.sendMessage(Language.getInstance().maxLevelEnchantment.replace("%l", Integer.toString(enchantment.getMaxLevel())));
                return true;
            } else {
                player.getInventory().getItemInMainHand().addEnchantment(enchantment, level);
            }
        } else {
            player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, level);
        }

        player.sendMessage(Language.getInstance().enchantmentAdded
                .replace("%e", enchantment.getName())
                .replace("%l", Integer.toString(level)));
        return true;
    }
}
