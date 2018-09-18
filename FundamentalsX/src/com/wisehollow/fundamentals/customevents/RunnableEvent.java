package com.wisehollow.fundamentals.customevents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by John on 10/15/2016.
 */
public class RunnableEvent extends Event implements Cancellable {
    private static HandlerList list = new HandlerList();

    public static HandlerList getHandlerList() {
        return list;
    }

    private boolean cancelled;
    //private HandlerList handlerList;

    public RunnableEvent() {
        cancelled = false;
        //handlerList = new HandlerList();
    }

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public void run() {
        if (cancelled)
            return;
    }
}
