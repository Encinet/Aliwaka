package com.obcbo.aliwaka.task.AntiCR;

import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.obcbo.aliwaka.file.Config.*;

// Anti Candles Running
public class CountChunk implements Listener {
    public static volatile Map<String, Integer> playerPoints = new ConcurrentHashMap<>();// 线程安全

    @EventHandler
    public void playerChunkLoadEvent(PlayerChunkLoadEvent event) {
        add(event.getPlayer().getName());
    }

    @EventHandler
    public void playerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        for (String n : crListenCommand) {
            if (event.getMessage().startsWith("/" + n)) {
                int set = playerPoints.get(event.getPlayer().getName()) >= crCommandImplement ?
                        (playerPoints.get(event.getPlayer().getName()) - crCommandImplement) : 0;
                playerPoints.put(event.getPlayer().getName(), set);
            }
        }
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        // 防止玩家退出无法恢复到正常速度
        if (!playerPoints.containsKey(event.getPlayer().getName())) {
            event.getPlayer().setWalkSpeed(crSpeedNormalWalk);
            event.getPlayer().setFlySpeed(crSpeedNormalFly);
        }
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        // 玩家退出时删除记录
        playerPoints.remove(event.getPlayer().getName());
    }

    private void add(String player) {
        if (playerPoints.containsKey(player)) {
            int now = playerPoints.get(player) + 1;
            playerPoints.put(player, now);
        } else {
            playerPoints.put(player, 1);
        }
    }
}
