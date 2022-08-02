package com.obcbo.aliwaka.file;

import com.obcbo.aliwaka.Aliwaka;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.obcbo.aliwaka.Aliwaka.logger;

public class Config {
    static final Plugin config = JavaPlugin.getProvidingPlugin(Aliwaka.class);
    public static int CD;
    public static int crCheckInterval;
    public static int crSpeedInterval;
    public static float crSpeedNormalWalk;
    public static float crSpeedNormalFly;
    public static float crSpeedLimitWalk;
    public static float crSpeedLimitFly;

    public static List<String> crListenCommand;
    public static int crCommandImplement;

    public static int crImplement;
    public static int crAfterImplement;
    public static int crFirstCondition;
    public static int crFirstReduce;
    public static int crSecondCondition;
    public static int crSecondReduce;

    public static int guardCheckInterval;
    public static int memPercentage;
    public static int tpsWarnThreshold;
    public static int tpsDangerThreshold;
    public static int msptDangerThreshold;

    public static @NotNull FileConfiguration getConfig() {
        return config.getConfig();
    }

    public static void load() {
        if (!(getConfig().getInt("version") == 3)) {
            logger.warning("配置文件config.yml是旧版");
        }

        CD = getConfig().getInt("CD", 10000);
        crCheckInterval = getConfig().getInt("AntiCR.check-interval", 10000);
        crListenCommand = getConfig().getStringList("AntiCR.listen-command.text");
        crCommandImplement = getConfig().getInt("AntiCR.listen-command.implement", 200);
        crSpeedInterval = getConfig().getInt("AntiCR.speed.interval", 10000);
        crSpeedNormalWalk = (float) getConfig().getDouble("AntiCR.speed.normal.walk", 0.2);
        crSpeedNormalFly = (float) getConfig().getDouble("AntiCR.speed.normal.fly", 0.1);
        crSpeedLimitWalk = (float) getConfig().getDouble("AntiCR.speed.limit.walk", 0.1);
        crSpeedLimitFly = (float) getConfig().getDouble("AntiCR.speed.limit.fly", 0.05);
        crImplement = getConfig().getInt("AntiCR.num.implement", 1500);
        crAfterImplement = getConfig().getInt("AntiCR.num.after-implement", 1000);
        crFirstCondition = getConfig().getInt("AntiCR.num.first-condition", 100);
        crFirstReduce = getConfig().getInt("AntiCR.num.first-reduce", 100);
        crSecondCondition = getConfig().getInt("AntiCR.num.second-condition", 10);
        crSecondReduce = getConfig().getInt("AntiCR.num.second-reduce", 10);

        guardCheckInterval = getConfig().getInt("Guard.check-interval", 10000);
        memPercentage = getConfig().getInt("Guard.mem-check.percentage", 90);
        tpsWarnThreshold = getConfig().getInt("Guard.tps-check.warn-threshold", 18);
        tpsDangerThreshold = getConfig().getInt("Guard.tps-check.danger-threshold", 3);
        msptDangerThreshold = getConfig().getInt("Guard.mspt-check.danger-threshold", 80);
    }
}
