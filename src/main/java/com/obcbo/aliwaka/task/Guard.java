package com.obcbo.aliwaka.task;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class Guard implements Runnable {
    public static int value = 0;// 危险值
    final int normalThresHold = 18;// 普通阈值
    final int dangerThresHold = 13;// 危险阈值

    @Override
    public void run() {
        while (true) {
            core();
        }
    }

    private void core() {
        int tps = (int) Bukkit.getTPS()[0];
        if (tps < dangerThresHold) {
            value = value + 5;
        } else if (tps < normalThresHold) {
            value++;
        } else if (value > 0) {
            value--;
        }
        if (value >= 25) {
            Bukkit.broadcast(Component.text("服务器开始强制回收内存,可能会有短暂卡顿"));
            long before = System.currentTimeMillis();
            System.gc();
            long total = System.currentTimeMillis() - before;
            Bukkit.broadcast(Component.text("服务器内存回收完成 耗时" + total + "ms"));
            if (value >= 50) {
                value = value - 50;
            } else {
                value = 0;
            }
        }

        try {
            Thread.sleep(10000);// 休眠10秒
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
