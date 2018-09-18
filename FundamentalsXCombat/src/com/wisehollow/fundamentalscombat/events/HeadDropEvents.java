package com.wisehollow.fundamentalscombat.events;

import com.wisehollow.fundamentalscombat.Settings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;

public class HeadDropEvents implements Listener {

    private final Random randomGenerator = new Random(System.currentTimeMillis());

    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null && randomGenerator.nextFloat() < Settings.playersDropHeadChance
                && event.getEntity().hasPermission("Fundamentals.HeadDrop")) {
            dropHead(event.getEntity());
        }
    }

    private void dropHead(final Player killed) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(killed);
        skullMeta.setDisplayName(killed.getName() + "'s head");
        playerHead.setItemMeta(skullMeta);
        killed.getWorld().dropItem(killed.getLocation(), playerHead);
    }

}
