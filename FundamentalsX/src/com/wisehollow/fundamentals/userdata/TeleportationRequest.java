package com.wisehollow.fundamentals.userdata;

import com.wisehollow.fundamentals.Language;
import com.wisehollow.fundamentals.Main;
import com.wisehollow.fundamentals.Settings;
import com.wisehollow.fundamentals.tasks.TeleportTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeleportationRequest {

    private Player initiator, target;
    private PlayerData initiatorData, targetData;

    private int taskId;

    public TeleportationRequest(Player initiator, Player target, PlayerData initiatorData, PlayerData targetData) {
        this.initiator = initiator;
        this.target = target;
        this.initiatorData = initiatorData;
        this.targetData = targetData;
        this.taskId = -1;
    }

    public void initialize() {
        initiator.sendMessage(Language.getInstance().teleportRequestSent);
        target.sendMessage(Language.getInstance().teleportRequestReceived.replace("%p", initiator.getName()));

        taskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            if (targetData.getTeleportationRequest().isPresent() && targetData.getTeleportationRequest().get().equals(this))
                targetData.clearTeleportationRequest();
        }, 20 * 60 * 2);
    }

    private void cancel() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            if (targetData.getTeleportationRequest().isPresent() && targetData.getTeleportationRequest().get().equals(this))
                targetData.clearTeleportationRequest();
        }
    }

    public void accept() {
        initiator.sendMessage(Language.getInstance().teleportRequestAccept);
        TeleportTask task = new TeleportTask(initiator, target.getLocation().clone(), Settings.TeleportDelay);
        task.run();

        cancel();
    }

    public void deny() {
        initiator.sendMessage(Language.getInstance().teleportRequestDeny);
        cancel();
    }

}
