package com.obcbo.aliwaka.task.AntiCR;

import com.obcbo.aliwaka.Aliwaka;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;

import static com.obcbo.aliwaka.Config.crCheckInterval;
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
                check();
                reduce();
                try {
                    Thread.sleep(crCheckInterval);// 休眠10秒
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
        Aliwaka.logger.warning("PointsChecker线程关闭");
    }

    private static void reduce() {
        for (Map.Entry<String, Integer> entry : playerPoints.entrySet()) {
            // key entry.getKey() ; value entry.getValue()
            if (entry.getValue() >= 4000) {
                playerPoints.put(entry.getKey(), 3800);
            } else if (entry.getValue() >= 30) {
                playerPoints.put(entry.getKey(), entry.getValue() - 30);
            } else if (entry.getValue() >= 10)  {
                playerPoints.put(entry.getKey(), entry.getValue() - 10);
            }
        }
    }

    private static void check() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            if (playerPoints.containsKey(a.getName())) {
                if (playerPoints.get(a.getName()) > 5000) {
                    Objects.requireNonNull(a.getPlayer()).sendTitle("§6旅行者 停下来休息一会吧", "跑图卡顿真的很严重", 10, 70, 20);
                }
            } else {
                playerPoints.put(a.getName(), 1);
            }
        }
    }
}
