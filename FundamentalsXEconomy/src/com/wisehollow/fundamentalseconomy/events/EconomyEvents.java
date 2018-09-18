package com.wisehollow.fundamentalseconomy.events;

import com.wisehollow.fundamentalseconomy.data.EconomyProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EconomyEvents implements Listener {

    @EventHandler
    public void loadProfileOnJoin(PlayerJoinEvent event) {
        EconomyProfile.load(event.getPlayer());
    }

    @EventHandler
    public void removeProfileOnQuit(PlayerQuitEvent event) {
        EconomyProfile.unload(event.getPlayer());
    }

}
