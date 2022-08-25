package com.obcbo.aliwaka.until;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Send {
    public static void text(String choose, String text) {
        // 值为console game all none
        switch (choose) {
            case "console":
                Bukkit.getConsoleSender().sendMessage(Component.text(text));
                return;
            case "game":
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(text);
                }
                return;
            case "all":
                Bukkit.broadcast(Component.text(text));
        }
    }
}
