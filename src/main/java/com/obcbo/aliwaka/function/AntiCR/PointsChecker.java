package com.obcbo.aliwaka.function.AntiCR;

import com.obcbo.aliwaka.Aliwaka;
import com.obcbo.aliwaka.file.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

import static com.obcbo.aliwaka.file.Config.*;
import static com.obcbo.aliwaka.file.Message.*;
import static com.obcbo.aliwaka.function.AntiCR.CountChunk.playerPoints;

public class PointsChecker implements Runnable {
    private static boolean on = true;
    static final Set<Player> controlList = new HashSet<>();
    public static Thread PointsChecker = new Thread(new PointsChecker(), "Aliwaka-PointsChecker");

    public static void start() {
        if (PointsChecker.isAlive()) {
            Aliwaka.logger.warning("PointsChecker is already turned on");
            return;
        }
        PointsChecker = new Thread(new PointsChecker(), "Aliwaka-PointsChecker");
        on = true;
        PointsChecker.start();
    }

    public static void stop() {
        on = false;
        if (PointsChecker.isAlive()) {
            PointsChecker.interrupt();
        }
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        Aliwaka.logger.info("PointsChecker starts to perform tasks");
        while (on) {
            try {
                check();
                reduce();
                speedControl();
                try {
                    Thread.sleep(crCheckInterval);// 休眠10秒
                } catch (InterruptedException e) {
                    Aliwaka.logger.warning("PointsChecker OFF");
                }
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
        Aliwaka.logger.warning("PointsChecker OFF");
    }

    private void speedControl() {
        Set<Player> set = new HashSet<>(controlList);
        for (Player player : set) {
            controlList.remove(player);
            player.sendMessage(Message.prefix + crLimit);
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
                player.sendMessage(Message.prefix + crLimitLifted);
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
                    Objects.requireNonNull(a.getPlayer()).sendTitle(crTitle, crSubtitle, 10, 70, 20);
                    controlList.add(a);
                }
            } else {
                playerPoints.put(a.getName(), 0);
            }
        }
    }
}