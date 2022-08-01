package com.obcbo.aliwaka.task.AntiCR;

import com.obcbo.aliwaka.Aliwaka;
import com.obcbo.aliwaka.file.Config;
import com.obcbo.aliwaka.file.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.obcbo.aliwaka.file.Config.*;
import static com.obcbo.aliwaka.task.AntiCR.CountChunk.playerPoints;

public class PointsChecker implements Runnable {
    private static boolean on = true;
    static final Set<Player> controlList = new HashSet<>();
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

    @SuppressWarnings("BusyWait")
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
            player.sendMessage(Message.prefix + "暂时限制速度");
            controlList.remove(player);
            player.setWalkSpeed(crSpeedLimitWalk);
            player.setFlySpeed(crSpeedLimitFly);
            new Thread(() -> {
                try {
                    Thread.sleep(crSpeedInterval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                player.setWalkSpeed(crSpeedNormalWalk);
                player.setFlySpeed(crSpeedNormalFly);
                player.sendMessage(Message.prefix + "恢复速度");
            }).start();
        }
    }

    private void reduce() {
        for (Map.Entry<String, Integer> entry : playerPoints.entrySet()) {
            // key entry.getKey() ; value entry.getValue()
            if (entry.getValue() >= crImplement) {
                playerPoints.put(entry.getKey(), crAfterImplement);
            } else if (entry.getValue() >= crFirstCondition) {
                playerPoints.put(entry.getKey(), entry.getValue() - crFirstReduce);
            } else if (entry.getValue() >= crSecondCondition) {
                playerPoints.put(entry.getKey(), entry.getValue() - crSecondReduce);
            }
        }
    }

    private void check() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            if (playerPoints.containsKey(a.getName())) {
                if (playerPoints.get(a.getName()) > crImplement) {
                    Objects.requireNonNull(a.getPlayer()).sendTitle("§6旅行者 停下来休息一会吧", "跑图卡顿真的很严重", 10, 70, 20);
                    controlList.add(a);
                }
            } else {
                playerPoints.put(a.getName(), 0);
            }
        }
    }
}