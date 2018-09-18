package com.wisehollow.fundamentals.listeners;

import com.wisehollow.fundamentals.Settings;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by John on 10/15/2016.
 */
public class SignColorEvents implements Listener {
    @EventHandler
    public void ApplyColor(SignChangeEvent event) {
        if (event.isCancelled() || !Settings.SignColor)
            return;

        for (int i = 0; i < event.getLines().length; i++) {
            String s = event.getLine(i);
            s = ChatColor.translateAlternateColorCodes('&', s);
            event.setLine(i, s);
        }
    }
}
