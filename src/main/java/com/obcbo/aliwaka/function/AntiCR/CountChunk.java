package com.obcbo.aliwaka.function.AntiCR;

import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.obcbo.aliwaka.file.Config.*;

// Anti Candles Running
public class CountChunk implements Listener {
    public static volatile Map<String, Integer> playerPoints = new ConcurrentHashMap<>();// 线程安全

    @EventHandler
    public void playerChunkLoadEvent(PlayerChunkLoadEvent event) {
        if (!crEnable) return;// 获取功能是否开启
        add(event.getPlayer().getName());
    }

    // 玩家重生事件
    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent event) {
        String name = event.getPlayer().getName();
        int set = playerPoints.get(name) >= crRespawn ?
                (playerPoints.get(name) - crRespawn) : 0;
        playerPoints.put(name, set);
    }

    @EventHandler
    public void playerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if (!crEnable) return;
        String name = event.getPlayer().getName();
        for (String n : crListenCommand) {
            if (event.getMessage().startsWith("/" + n)) {
                int set = playerPoints.get(name) >= crCommandImplement ?
                        (playerPoints.get(name) - crCommandImplement) : 0;
                playerPoints.put(name, set);
            }
        }
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        if (!crEnable) return;
        // 防止玩家退出无法恢复到正常速度
        Player player = event.getPlayer();
        if (!playerPoints.containsKey(player.getName())) {
            player.setWalkSpeed(crSpeedNormalWalk);
            player.setFlySpeed(crSpeedNormalFly);
        }
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        if (!crEnable) return;
        // 玩家退出时删除记录
        playerPoints.remove(event.getPlayer().getName());
    }

    private void add(String player) {
        if (!crEnable) return;
        if (playerPoints.containsKey(player)) {
            int now = playerPoints.get(player) + 1;
            playerPoints.put(player, now);
        } else {
            playerPoints.put(player, 1);
        }
    }
}
