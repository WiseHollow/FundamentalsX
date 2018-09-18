package com.wisehollow.fundamentals.customevents;

import com.wisehollow.fundamentals.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/15/2016.
 */
public class SendPrivateMessageEvent extends RunnableEvent {
    private CommandSender sender;
    private CommandSender receiver;
    private String message;
    private String senderPrefix;
    private String receiverPrefix;

    public SendPrivateMessageEvent(CommandSender s, CommandSender r, String m) {
        super();
        sender = s;
        receiver = r;
        message = m;

        String sPrefix = "";
        String rPrefix = "";
        if (sender instanceof Player)
            sPrefix = PlayerUtil.GetPlayerPrefix((Player) sender);
        if (receiver instanceof Player)
            rPrefix = PlayerUtil.GetPlayerPrefix((Player) receiver);

        senderPrefix = " -> " + ChatColor.BOLD + rPrefix + receiver.getName() + ChatColor.RESET + " | ";
        receiverPrefix = ChatColor.BOLD + sPrefix + sender.getName() + ChatColor.RESET + " -> " + rPrefix + receiver.getName() + " | ";
    }

    public CommandSender getReceiver() {
        return receiver;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    //public String getSenderPrefix() { return senderPrefix; }
    public String getReceiverPrefix() {
        return receiverPrefix;
    }

    @Override
    public void run() {
        super.run();
        sender.sendMessage(senderPrefix + message);
        receiver.sendMessage(receiverPrefix + message);
    }
}
