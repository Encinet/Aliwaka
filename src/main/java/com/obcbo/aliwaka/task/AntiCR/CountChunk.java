package com.obcbo.aliwaka.task.AntiCR;

import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

import static com.obcbo.aliwaka.Config.crSpeedNormalFly;
import static com.obcbo.aliwaka.Config.crSpeedNormalWalk;

// Anti Candles Running
public class CountChunk implements Listener {
    public static final Map<String, Integer> playerPoints = new HashMap<>();

    @EventHandler
    public void playerChunkLoadEvent(PlayerChunkLoadEvent event) {
        add(event.getPlayer().getName());
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
