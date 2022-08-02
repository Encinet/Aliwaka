package com.obcbo.aliwaka.task;

import com.obcbo.aliwaka.Aliwaka;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.text.DecimalFormat;
import java.util.Objects;

import static com.obcbo.aliwaka.file.Config.*;
import static com.obcbo.aliwaka.file.Message.gcEnd;
import static com.obcbo.aliwaka.file.Message.gcStart;

public class Guard implements Runnable {
    public static final Thread guard = new Thread(new Guard(), "Aliwaka-Guard");
    private static boolean on = true;
    private static int warn = 0;// 危险值

    public static void start() {
        if (guard.isAlive()) {
            Aliwaka.logger.warning("Guard已开启");
            return;
        }
        guard.start();
    }

    public static void stop() {
        on = false;
    }

    public static int getWarn() {
        return warn;
    }

    @Override
    public void run() {
        Aliwaka.logger.info("Guard开始执行任务");
        while (on) {
            try {
                core();
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        Aliwaka.logger.warning("Guard线程关闭");
    }

    private void core() {
        if (memEnable) {
            memcheck();
        }
        if (tpsEnable) {
            tpscheck();
        }
        if (msptEnable) {
            msptcheck();
        }
        try {
            Thread.sleep(guardCheckInterval);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void msptcheck() {
        DecimalFormat df = new DecimalFormat("#.00");// 保留小数点后两位
        double mspt = Double.parseDouble(df.format(Bukkit.getTickTimes()[0] / 1000000));
        if (mspt >= msptDangerThreshold) {
            gc();
        }
    }

    private void memcheck() {
        // 内存百分比
        double ratio = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (double) Runtime.getRuntime().maxMemory() * 100;
        if (ratio >= memPercentage) {
            gc();
        }
    }

    private void tpscheck() {
        int tps = (int) Bukkit.getTPS()[0];
        if (tps < tpsDangerThreshold) {
            gc();
            chunk();
        } else if (tps < tpsWarnThreshold) {
            warn = warn + (20 - tps);
        } else if (tps > tpsWarnThreshold && warn > 0) {
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
        Bukkit.broadcast(Component.text(gcStart));
        long before = System.currentTimeMillis();
        System.gc();
        long total = System.currentTimeMillis() - before;
        Bukkit.broadcast(Component.text(gcEnd.replace("%time%", Long.toString(total))));
    }

    private void chunk() {
        Chunk[] chunk = Objects.requireNonNull(Bukkit.getWorld("world")).getLoadedChunks();
        for (Chunk i : chunk) {
            i.unload();
        }
    }
}
