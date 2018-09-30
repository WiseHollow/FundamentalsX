package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.utils.PlayerUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by John on 10/13/2016.
 */
public class CommandHeal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getInstance().notLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.Heal")) {
            sender.sendMessage(Language.getInstance().unauthorized);
            return true;
        }

        if (args.length > 0 && sender.hasPermission("Fundamentals.Heal.Other")) {
            player = PlayerUtil.GetPlayer(args[0]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Language.getInstance().targetNotOnline);
                return true;
            }

            sender.sendMessage(Language.getInstance().healedPlayer.replace("%p", player.getName()));
            player.sendMessage(Language.getInstance().healed);
        } else {
            sender.sendMessage(Language.getInstance().healed);
        }

        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        player.setFoodLevel(20);
        player.setFireTicks(0);
        for (PotionEffectType t : PotionEffectType.values())
            if (t != null)
                player.removePotionEffect(t);

        return true;
    }
}
