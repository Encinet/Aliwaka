package com.obcbo.aliwaka.qq;

import com.obcbo.aliwaka.Config;
import me.dreamvoid.miraimc.api.MiraiBot;

public class SendMessage {
    public static void sendGroup(String message) {
        MiraiBot.getBot(Config.getConfigLong("account.bot")).getGroup(Config.getConfigLong("account.group")).sendMessageMirai(message);
    }
    public static void sendFriend(String message,long SenderID) {
        MiraiBot.getBot(Config.getConfigLong("account.bot")).getFriend(SenderID).sendMessageMirai(message);
    }
}
