package com.obcbo.aliwaka.task;

import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// Anti Candles Running
public class AntiCR extends PlayerChunkLoadEvent {
    public AntiCR(@NotNull Chunk chunk, @NotNull Player player) {
        super(chunk, player);
    }
}
