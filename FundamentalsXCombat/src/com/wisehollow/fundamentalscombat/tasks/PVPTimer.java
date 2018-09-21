package com.wisehollow.fundamentalscombat.tasks;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentalscombat.Main;
import com.wisehollow.fundamentalscombat.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class PVPTimer implements Listener {

    private static HashMap<UUID, PVPTimer> timers = new HashMap<>();
    public static boolean disableTimer(Player player) {
        if (timers.containsKey(player.getUniqueId())) {
            timers.get(player.getUniqueId()).cancel();
            timers.remove(player.getUniqueId());
            return true;
        } else {
            return false;
        }
    }

    private final Player player;
    private final int minutes;
    private boolean started;
    private BukkitTask bukkitTask;

    public PVPTimer(final Player player, final int minutes) {
        this.player = player;
        this.minutes = minutes;
        this.bukkitTask = null;
    }

    public boolean start() {
        if (!started) {
            started = true;

            register();
            scheduleCancel();
            timers.put(player.getUniqueId(), this);
            if (player != null && player.isOnline()) {
                player.sendMessage(Language.PVPTimerEnabled.replace("%m", Integer.toString(Settings.delayedPVPMinutes)));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean cancel() {
        if (started) {
            started = false;

            unregister();
            if (bukkitTask != null) {
                bukkitTask.cancel();
            }
            if (player != null && player.isOnline()) {
                player.sendMessage(Language.PVPTimerDisabled);
            }

            return true;
        } else {
            return false;
        }
    }

    private void scheduleCancel() {
        bukkitTask = Bukkit.getScheduler().runTaskLater(Main.getPlugin(), this::cancel, 1200 * minutes);
    }

    private void register() {
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
    }

    private void unregister() {
        EntityDamageByEntityEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void preventPVP(EntityDamageByEntityEvent event) {
        if (event.getEntity().equals(player)) {
            boolean cancel = false;
            Player damager = null;
            if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) event.getDamager();
                if (projectile.getShooter() != null && projectile.getShooter() instanceof Player) {
                    cancel = true;
                    damager = (Player) projectile.getShooter();
                }
            } else if (event.getDamager() instanceof Player) {
                cancel = true;
                damager = (Player) event.getDamager();
            }

            if (cancel) {
                event.setCancelled(true);
                damager.sendMessage(Language.CannotPVPWithPlayer);
            }
        }
    }

}
