package com.obcbo.aliwaka.qq.event;

import com.obcbo.aliwaka.qq.SendMessage;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiFriendMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FriendMessageReceiveEvent implements Listener {
    @EventHandler
    public void onFriendMessageReceive(MiraiFriendMessageEvent event) {
        long senderID = event.getSenderID();
        String message = event.getMessage();

        if (!".".equals(message.substring(0,1))) {
            return;
        } else {
            message = message.substring(1);
        }
        MiraiBot.getBot(event.getBotID()).getFriend(event.getSenderID()).sendMessageMirai(message);

        SendMessage.sendFriend(message, event.getSenderID());
    }
}