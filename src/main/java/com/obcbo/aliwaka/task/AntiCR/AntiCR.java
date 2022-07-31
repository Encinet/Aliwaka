package com.obcbo.aliwaka.task.AntiCR;

import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

// Anti Candles Running
public class AntiCR implements Listener {
    public static final Map<String, Integer> playerPoints = new HashMap<>();

    @EventHandler
    public void playerChunkLoadEvent(PlayerChunkLoadEvent event) {
        add(event.getPlayer().getName());
    }
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
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
