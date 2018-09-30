package com.wisehollow.fundamentalseconomy.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEcon implements CommandExecutor
{
    private enum CommandType { GIVE, TAKE, SET }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Econ"))
        {
            player.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length == 3) {

            CommandType commandType;

            try {
                commandType = CommandType.valueOf(args[0].toUpperCase());
            } catch (Exception exception) {
                return false;
            }

            final Player target = Bukkit.getPlayer(args[1]);
            Double amount;

            if (target == null) {
                player.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }

            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException exception) {
                player.sendMessage(Language.getInstance().mustBeANumber);
                return true;
            }

            double afterBalance = 0;

            switch (commandType) {
                case GIVE:
                    EconomyResponse depositResponse = Main.getPlugin().getEconomy().depositPlayer(target, amount);
                    if (depositResponse.errorMessage != null) {
                        Main.getPlugin().getEconomy().depositPlayer(player, amount);
                        player.sendMessage(Language.getInstance().prefixWarning + depositResponse.errorMessage);
                        return true;
                    } else
                        afterBalance = depositResponse.balance;
                    break;
                case TAKE:
                    EconomyResponse withdrawResponse = Main.getPlugin().getEconomy().withdrawPlayer(target, amount);
                    if (withdrawResponse.errorMessage != null) {
                        player.sendMessage(Language.getInstance().prefixWarning + withdrawResponse.errorMessage);
                        return true;
                    } else
                        afterBalance = withdrawResponse.balance;
                    break;
                case SET:
                    double balance = Main.getPlugin().getEconomy().getBalance(target);
                    EconomyResponse setResponse = Main.getPlugin().getEconomy().withdrawPlayer(target, balance);
                    if (setResponse.errorMessage == null) {
                        if (amount > 0) {
                            setResponse = Main.getPlugin().getEconomy().depositPlayer(target, amount);
                            if (setResponse.errorMessage != null) {
                                player.sendMessage(Language.getInstance().prefixWarning + setResponse.errorMessage);
                                return true;
                            } else
                                afterBalance = setResponse.balance;
                        } else if (amount < 0) {
                            setResponse = Main.getPlugin().getEconomy().withdrawPlayer(target, -amount);
                            if (setResponse.errorMessage != null) {
                                player.sendMessage(Language.getInstance().prefixWarning + setResponse.errorMessage);
                                return true;
                            } else
                                afterBalance = setResponse.balance;
                        }
                    } else {
                        player.sendMessage(Language.getInstance().prefixWarning + setResponse.errorMessage);
                        return true;
                    }
                    break;
            }

            String message = Language.getInstance().moneySet
                    .replace("%1", afterBalance + " " + (afterBalance == 1 ? Main.getPlugin().getEconomy().currencyNameSingular() : Main.getPlugin().getEconomy().currencyNamePlural()));
            player.sendMessage(message);

        } else {
            return false;
        }

        return true;
    }
}