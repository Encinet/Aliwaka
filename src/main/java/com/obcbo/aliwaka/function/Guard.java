package com.obcbo.aliwaka.function;

import com.obcbo.aliwaka.Aliwaka;
import com.obcbo.aliwaka.until.Send;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.text.DecimalFormat;
import java.util.Objects;

import static com.obcbo.aliwaka.file.Config.*;
import static com.obcbo.aliwaka.file.Message.gcEnd;
import static com.obcbo.aliwaka.file.Message.gcStart;

public class Guard implements Runnable {
    public static Thread guard = new Thread(new Guard(), "Aliwaka-Guard");
    private static boolean on = true;
    private static int warn = 0;// 危险值

    public static void start() {
        if (guard.isAlive()) {
            Aliwaka.logger.warning("Guard is already turned on");
            return;
        }
        guard = new Thread(new Guard(), "Aliwaka-Guard");
        on = true;
        guard.start();
    }

    public static void stop() {
        on = false;
        if(guard.isAlive()) {
            guard.interrupt();
        }
    }

    public static int getWarn() {
        return warn;
    }

    @Override
    public void run() {
        Aliwaka.logger.info("Guard starts to perform tasks");
        while (on) {
            try {
                core();
            } catch (RuntimeException e) {
                Aliwaka.logger.warning("Guard OFF");
            }
        }
        Aliwaka.logger.warning("Guard OFF");
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
        execute();
        try {
            Thread.sleep(guardCheckInterval);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void execute() {
        if (warn >= 20) {
            gc();
            if (warn >= 20) {
                warn = warn - 5;
            } else {
                warn = 0;
            }
        }
    }

    private void msptcheck() {
        DecimalFormat df = new DecimalFormat("#.00");// 保留小数点后两位
        double mspt = Double.parseDouble(df.format(Bukkit.getTickTimes()[0] / 1000000));
        if (mspt >= msptDangerThreshold) {
            warn = warn + 7;
        }
    }

    private void memcheck() {
        // 内存百分比
        double ratio = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (double) Runtime.getRuntime().maxMemory() * 100;
        if (ratio >= memPercentage) {
            warn = warn + 10;
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
    }

    public static void gc() {
        Send.text(gcOutput, gcStart);
        long before = System.currentTimeMillis();
        System.gc();
        long total = System.currentTimeMillis() - before;
        Send.text(gcOutput, gcEnd.replace("%time%", Long.toString(total)));
    }

    private void chunk() {
        Chunk[] chunk = Objects.requireNonNull(Bukkit.getWorld("world")).getLoadedChunks();
        for (Chunk i : chunk) {
            i.unload();
        }
    }
}
