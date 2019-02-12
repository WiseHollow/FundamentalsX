package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.List;

public class CommandFireball implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Fireball")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        Class fireballClass = Fireball.class;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("dragon")) {
                fireballClass = DragonFireball.class;
            } else if (args[0].equalsIgnoreCase("w") || args[0].equalsIgnoreCase("wither")) {
                fireballClass = WitherSkull.class;
            }
        }

        Fireball fireball = (Fireball) player.launchProjectile(fireballClass);
        fireball.setIsIncendiary(false);
        fireball.setBounce(false);
        fireball.setVelocity(fireball.getDirection().clone().normalize().multiply(1.3));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> options = new ArrayList<>();

        options.add("d");
        options.add("dragon");
        options.add("w");
        options.add("wither");

        return options;
    }

}
