package com.wisehollow.fundamentalseconomy.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPay implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Pay")) {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 2) {
            final Player target = Bukkit.getPlayer(args[0]);
            Double amount;

            if (target == null) {
                player.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            } else if (target.equals(player)) {
                player.sendMessage(Language.getInstance().cannotSendMoneyToSelf);
                return true;
            }

            try {
                amount = Double.parseDouble(args[1]);
            } catch (NumberFormatException exception) {
                player.sendMessage(Language.getInstance().mustBeANumber);
                return true;
            }

            EconomyResponse withdrawResponse = Main.getPlugin().getEconomy().withdrawPlayer(player, amount);
            if (withdrawResponse.errorMessage != null) {
                player.sendMessage(Language.getInstance().prefixWarning + withdrawResponse.errorMessage);
                return true;
            }

            EconomyResponse depositResponse = Main.getPlugin().getEconomy().depositPlayer(target, amount);
            if (depositResponse.errorMessage != null) {
                Main.getPlugin().getEconomy().depositPlayer(player, amount);
                player.sendMessage(Language.getInstance().prefixWarning + depositResponse.errorMessage);
                return true;
            }

            String toSender = Language.getInstance().moneySent
                    .replace("%1", amount + " " + (amount == 1 ? Main.getPlugin().getEconomy().currencyNameSingular() : Main.getPlugin().getEconomy().currencyNamePlural()))
                    .replace("%2", target.getName());
            String toReceiver = Language.getInstance().moneyReceived
                    .replace("%1", amount + " " + (amount == 1 ? Main.getPlugin().getEconomy().currencyNameSingular() : Main.getPlugin().getEconomy().currencyNamePlural()))
                    .replace("%2", target.getName());
            player.sendMessage(toSender);
            target.sendMessage(toReceiver);

        } else {
            return false;
        }

        return true;
    }
}