package com.wisehollow.fundamentals.tasks;

import com.wisehollow.fundamentals.Kit;
import com.wisehollow.fundamentals.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/20/2016.
 */
public class KitTask implements CustomTask, Listener {
    private static List<KitTask> taskList = new ArrayList<>();

    public static KitTask GetTask(Player player) {
        for (KitTask task : taskList) {
            if (task.player.equals(player))
                return task;
        }

        return null;
    }

    private Player player;
    private Kit kit;
    private int taskID;
    private long timeRun;

    public KitTask(Player player, Kit kit) {
        this.player = player;
        this.kit = kit;
        this.taskID = -1;
    }

    public int getSecondsLeft() {
        return (int) (Clock.systemDefaultZone().millis() - timeRun) / 1000;
    }

    @Override
    public boolean run() {
        if (taskList.contains(this))
            return false;

        kit.give(player);
        timeRun = Clock.systemDefaultZone().millis();

        if (kit.getDelay() > 0) {
            taskList.add(this);

            taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> disable(), 20 * kit.getDelay());
        }

        return true;
    }

    @Override
    public void disable() {
        taskList.remove(this);
        if (taskID != -1) {
            Bukkit.getScheduler().cancelTask(taskID);
            taskID = -1;
        }
    }
}
