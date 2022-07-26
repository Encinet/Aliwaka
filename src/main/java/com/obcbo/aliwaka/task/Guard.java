package com.obcbo.aliwaka.task;

import com.obcbo.aliwaka.Config;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class Guard implements Runnable {
    private final Logger logger = Logger.getLogger("Aliwaka");
    private static boolean on = true;
    private static int warn = 0;// 危险值
    final int tpsthresHold = 18;// 处理阈值
    final int tpswaring = 3;// 严重阈值
    final int memPercentage = 80;// 内存警告阈值

    public static void start() {
        new Thread(new Guard(), "GuardThread").start();
    }
    public static void stop() {
        on = false;
    }
    public static int getwarn() {
        return warn;
    }

    @Override
    public void run() {
        while (on) {
            try {
                core();
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        logger.warning("Guard线程关闭");
    }

    private void core() {
        tpscheck();
        memcheck();
        try {
            Thread.sleep(10000);// 休眠10秒
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void memcheck() {
        // 内存百分比
        double ratio = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (double) Runtime.getRuntime().maxMemory() *100;
        if (ratio >= memPercentage) {
            gc();
        }
    }

    private void tpscheck() {
        int tps = (int) Bukkit.getTPS()[0];
        if (tps < tpswaring) {
            gc();
            chunk();
        } else if (tps < tpsthresHold) {
            warn = warn + (20 - tps);
        } else if (tps >= tpsthresHold && warn > 0) {
            warn--;
        }
        if (warn >= 10) {
            gc();
            if (warn >= 10) {
                warn = warn - 5;
            } else {
                warn = 0;
            }
        }
    }

    private void gc() {
        Bukkit.broadcast(Component.text("服务器开始强制回收内存,可能会有短暂卡顿"));
        long before = System.currentTimeMillis();
        System.gc();
        long total = System.currentTimeMillis() - before;
        Bukkit.broadcast(Component.text("服务器内存回收完成 耗时" + total + "ms"));
    }
    private void chunk() {
        Chunk[] chunk = Bukkit.getWorld("world").getLoadedChunks();
        for (Chunk i: chunk) {
            i.unload();
        }
    }
}
