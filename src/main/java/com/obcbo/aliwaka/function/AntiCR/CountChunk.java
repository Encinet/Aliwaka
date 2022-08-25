package com.obcbo.aliwaka.function.AntiCR;

import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.obcbo.aliwaka.file.Config.*;

// Anti Candles Running
public class CountChunk implements Listener {
    public static final Map<String, Integer> playerPoints = new ConcurrentHashMap<>();// 线程安全

    @EventHandler
    public void playerChunkLoadEvent(PlayerChunkLoadEvent event) {
        if (!crEnable) return;// 获取功能是否开启
        if (dontCheck(event.getPlayer())) return;

        String name = event.getPlayer().getName();
        if (playerPoints.containsKey(name)) {
            int now = playerPoints.get(name) + 1;
            playerPoints.put(name, now);
        } else {
            playerPoints.put(name, 1);
        }
    }

    // 玩家重生事件
    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent event) {
        if (!crEnable) return;
        if (dontCheck(event.getPlayer())) return;
        String name = event.getPlayer().getName();
        int set = playerPoints.get(name) >= crRespawn ?
                (playerPoints.get(name) - crRespawn) : 0;
        playerPoints.put(name, set);
    }

    @EventHandler
    public void playerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if (!crEnable) return;
        if (dontCheck(event.getPlayer())) return;
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
        if (dontCheck(event.getPlayer())) return;
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
        if (dontCheck(event.getPlayer())) return;
        // 玩家退出时删除记录
        playerPoints.remove(event.getPlayer().getName());
    }

    // 根据世界和游戏模式判断是否需要检测
    public static boolean dontCheck(Player player) {
        for (String world : crDontCheckWorlds) {
            if (world.equals(player.getWorld().getName())) {
                return true;
            }
        }
        for (String gamemode : crDontCheckGamemodes) {
            if (gamemode.equals(player.getGameMode().name())) {
                return true;
            }
        }
        return false;
    }
}
