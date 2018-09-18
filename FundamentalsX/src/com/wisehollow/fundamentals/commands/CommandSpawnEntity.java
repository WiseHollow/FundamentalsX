package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Settings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * Created by John on 10/13/2016.
 */
public class CommandSpawnEntity implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.YouMustBeLoggedIn);
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("Fundamentals.SpawnEntity")) {
            player.sendMessage(Language.DoesNotHavePermission);
            return true;
        }

        if (args.length == 0)
            return false;

        Integer amount = 1;
        if (args.length == 2) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                sender.sendMessage(Language.PREFIX + "'" + amount + "' is not an number.");
            }
        }

        EntityType type;
        boolean tamed = false;
        String[] typeWithArg = args[0].split(":");
        try {
            type = EntityType.valueOf(typeWithArg[0].toUpperCase());
        } catch (Exception exception) {
            sender.sendMessage(Language.PREFIX_WARNING + "Invalid entity type.");
            return true;
        }

        if (typeWithArg.length > 1 && typeWithArg[1].equalsIgnoreCase("tamed")) {
            tamed = true;
        }

        Set<Material> s = null;
        Location target = player.getTargetBlock(s, 100).getLocation().clone().add(0.5, 1, 0.5);
        for (int i = 0; i < (amount > Settings.SpawnMobLimit ? Settings.SpawnMobLimit : amount); i++) {
            LivingEntity spawnedEntity = (LivingEntity) target.getWorld().spawnEntity(target, type);
            if (tamed && spawnedEntity instanceof Tameable) {
                Tameable tameable = (Tameable) spawnedEntity;
                tameable.setOwner(player);
                tameable.setTamed(true);
                if (spawnedEntity instanceof Horse) {
                    ((Horse) spawnedEntity).getInventory().setSaddle(new ItemStack(Material.SADDLE));
                }
            }
        }
        sender.sendMessage(Language.PREFIX + amount + " " + type.name() + " spawned at location.");

        return true;
    }
}
