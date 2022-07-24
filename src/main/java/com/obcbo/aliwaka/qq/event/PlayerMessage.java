package com.obcbo.aliwaka.qq.event;

import com.obcbo.aliwaka.Config;
import com.obcbo.aliwaka.qq.SendMessage;
import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import io.papermc.paper.event.player.AsyncChatEvent;

public class PlayerMessage implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e) {
        String message = ChatColor.stripColor(String.valueOf(e.message()));
        System.out.println("[" + message + "]");
        if ("#".equals(message.substring(0,1))) {
            String formatText = Config.getConfigString("bot.group-chat-format")
                    .replace("$[player]", e.getPlayer().getName())
                    .replace("$[message]", message);
            SendMessage.sendGroup(message.substring(1));
        }
    }
}
