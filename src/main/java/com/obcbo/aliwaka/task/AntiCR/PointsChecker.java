package com.obcbo.aliwaka.task.AntiCR;

import com.obcbo.aliwaka.Aliwaka;
import com.obcbo.aliwaka.Config;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

import static com.obcbo.aliwaka.Config.crCheckInterval;
import static com.obcbo.aliwaka.task.AntiCR.AntiCR.playerPoints;

public class PointsChecker implements Runnable {
    private static boolean on = true;
    static Set<Player> controlList = new HashSet<>();
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
                speedControl();
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

    private void speedControl() {
        for (Player player : controlList) {
            controlList.remove(player);
            player.setWalkSpeed((float) 0.1);
            player.setFlySpeed((float) 0.05);
            new Thread(() -> {
                try {
                    Thread.sleep(10000);// 休眠10秒
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                player.setWalkSpeed((float) 0.2);
                player.setFlySpeed((float) 0.1);
                player.sendMessage(Config.prefix + "恢复速度");
            }).start();
        }
    }

    private void reduce() {
        for (Map.Entry<String, Integer> entry : playerPoints.entrySet()) {
            // key entry.getKey() ; value entry.getValue()
            if (entry.getValue() >= 2000) {
                playerPoints.put(entry.getKey(), 1500);
            } else if (entry.getValue() >= 100) {
                playerPoints.put(entry.getKey(), entry.getValue() - 100);
            } else if (entry.getValue() >= 10) {
                playerPoints.put(entry.getKey(), entry.getValue() - 10);
            }
        }
    }

    private void check() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            if (playerPoints.containsKey(a.getName())) {
                if (playerPoints.get(a.getName()) > 2000) {
                    Objects.requireNonNull(a.getPlayer()).sendTitle("§6旅行者 停下来休息一会吧", "跑图卡顿真的很严重", 10, 70, 20);
                    controlList.add(a);
                }
            } else {
                playerPoints.put(a.getName(), 0);
            }
        }
    }
}