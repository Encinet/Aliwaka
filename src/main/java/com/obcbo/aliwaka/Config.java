package com.obcbo.aliwaka;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Config {
    static final Plugin config = JavaPlugin.getProvidingPlugin(Aliwaka.class);
    public static int version;
    public static String prefix;
    public static String colorNormal;
    public static String colorWarn;
    public static String colorDanger;
    public static int crCheckInterval;
    public static int guardCheckInterval;
    public static int memPercentage;
    public static int tpsWarnThreshold;
    public static int tpsDangerThreshold;
    public static int msptDangerThreshold;

    public static @NotNull FileConfiguration getConfig() {
        return config.getConfig();
    }

    public static void load() {
        version = getConfig().getInt("version");
        prefix = getConfig().getString("prefix");
        colorNormal = getConfig().getString("color.normal");
        colorWarn = getConfig().getString("color.warn");
        colorDanger = getConfig().getString("color.danger");

        crCheckInterval = getConfig().getInt("AntiCR.check-interval");
        guardCheckInterval = getConfig().getInt("Guard.check-interval");
        memPercentage = getConfig().getInt("Guard.mem-check.percentage");
        tpsWarnThreshold = getConfig().getInt("Guard.tps-check.warn-threshold");
        tpsDangerThreshold = getConfig().getInt("Guard.tps-check.danger-threshold");
        msptDangerThreshold = getConfig().getInt("Guard.mspt-check.danger-threshold");
    }
}
