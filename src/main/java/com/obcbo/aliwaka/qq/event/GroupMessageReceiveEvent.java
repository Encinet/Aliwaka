package com.obcbo.aliwaka.qq.event;

import com.obcbo.aliwaka.Config;
import com.obcbo.aliwaka.qq.SendMessage;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GroupMessageReceiveEvent implements Listener {
    @EventHandler
    public void onFriendMessageReceive(MiraiGroupMessageEvent event) {
        long senderID = event.getSenderID();
        String message = event.getMessage();

        // 群向服发送消息
        if ("#".equals(message.substring(0,1))) {
            String formatText = Config.getConfigString("format.qq-to-server")
                    .replace("$[nick]", event.getSenderName())
                    .replace("$[qq]",String.valueOf(event.getSenderID()))
                    .replace("$[message]", message.substring(1));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',formatText));
            return;
        }

        if (!".".equals(message.substring(0,1))) {
            return;
        } else {
            message = message.substring(1);
        }
        SendMessage.sendGroup(message);
    }
}
