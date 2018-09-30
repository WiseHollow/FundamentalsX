package com.wisehollow.fundamentalscombat.events;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentalscombat.Settings;
import com.wisehollow.fundamentalscombat.tasks.PVPTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PVPTimerEvents implements Listener {

    @EventHandler
    public void enableOnFirstJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            new PVPTimer(event.getPlayer(), Settings.delayedPVPMinutes).start();
            event.getPlayer().sendMessage(Language.getInstance().pvpTimerHowTo);
        }
    }

}
