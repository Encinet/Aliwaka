package com.obcbo.aliwaka.task.AntiCR;

import com.obcbo.aliwaka.Aliwaka;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

import static com.obcbo.aliwaka.task.AntiCR.AntiCR.playerPoints;

public class PointsChecker implements Runnable {
    private static boolean on = true;
    private static final Thread PointsChecker = new Thread(new PointsChecker(), "Aliwaka-PointsChecker");

    public static void start() {
        if (PointsChecker.isAlive()) {
            Aliwaka.logger.warning("PointsChecker已开启");
            return;
        }
        PointsChecker.start();
    }

    public static void stop() {
        on = false;
    }

    @Override
    public void run() {
        Aliwaka.logger.info("PointsChecker开始执行任务");
        while (on) {
            try {
                del();
                check();
                try {
                    Thread.sleep(10000);// 休眠10秒
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
        Aliwaka.logger.warning("PointsChecker线程关闭");
    }

    private static void del() {

    }

    private static void check() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            if (playerPoints.get(a.getName()) > 1000) {
                Objects.requireNonNull(a.getPlayer()).sendTitle("§6旅行者 停下来休息一会吧", "跑图卡顿真的很严重", 10, 70, 20);
            }
        }
    }
}
