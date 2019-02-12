package com.wisehollow.fundamentalscombat.events;

import com.wisehollow.fundamentalscombat.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class CombatLoggingEvents implements Listener {

    private static final HashMap<UUID, Long> combatLog = new HashMap<>();
    private static final Long combatLogTime = 15000L;

    private void punish(Player player) {
        player.setHealth(0d);
    }

    private void setLog(Player damager, Player damaged) {
        damaged.sendMessage(ChatColor.RED + "You have engaged in combat. ");
        combatLog.put(damager.getUniqueId(), System.currentTimeMillis());
        combatLog.put(damaged.getUniqueId(), System.currentTimeMillis());
    }

    private void clearLog(Player damager, Player damaged) {
        clearLog(damager);
        clearLog(damaged);
    }

    private void clearLog(Player player) {
        combatLog.remove(player.getUniqueId());
    }

    private boolean isCombatLogging(Player player) {
        if (combatLog.containsKey(player.getUniqueId())) {
            Long combatTime = combatLog.get(player.getUniqueId());
            Long difference = System.currentTimeMillis() - combatTime;
            if (difference < combatLogTime) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void quitDuringCombat(PlayerQuitEvent event) {
        if (isCombatLogging(event.getPlayer())) {
            // They left too early!
            punish(event.getPlayer());
            Main.getPlugin().getLogger().warning("Combat Logging detected from: " +
                    event.getPlayer().getName() + " with UUID" + event.getPlayer().getUniqueId().toString());
            clearLog(event.getPlayer());
        }
    }

    @EventHandler
    public void recordCombatTime(EntityDamageByEntityEvent event) {
        if (!event.isCancelled()) {
            if (event.getEntity() instanceof Player) {

                Player damaged = (Player) event.getEntity();
                Player damager = null;

                if (event.getDamager() instanceof Player) {
                    damager = (Player) event.getDamager();
                } else if (event.getDamager() instanceof Projectile &&
                        ((Projectile) event.getDamager()).getShooter() != null &&
                        ((Projectile) event.getDamager()).getShooter() instanceof Player) {
                    damager = (Player) ((Projectile) event.getDamager()).getShooter();
                }

                if (damager != null) {
                    if (damaged.getHealth() - event.getDamage() <= 0) {
                        clearLog(damager, damaged);
                    } else {
                        setLog(damager, damaged);
                    }
                }
            }
        }
    }

}
