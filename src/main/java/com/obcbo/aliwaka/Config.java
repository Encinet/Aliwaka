package com.obcbo.aliwaka;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Config {
    static Plugin config = JavaPlugin.getProvidingPlugin(Aliwaka.class);
    public static int version;
    public static String prefix;
    public static String dateFormat;
    public static String timeFormat;
    public static String timeZone;
    public static String colorNormal;
    public static String colorWarn;
    public static String colorDanger;
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
        dateFormat = getConfig().getString("placeholders.dateFormat");
        timeFormat = getConfig().getString("placeholders.timeFormat");
        timeZone = getConfig().getString("placeholders.timeZone");
        colorNormal = getConfig().getString("color.normal");
        colorWarn = getConfig().getString("color.warn");
        colorDanger = getConfig().getString("color.danger");
        memPercentage = getConfig().getInt("guard.mem-check.percentage");
        tpsWarnThreshold = getConfig().getInt("guard.tps-check.warn-threshold");
        tpsDangerThreshold = getConfig().getInt("guard.tps-check.danger-threshold");
        msptDangerThreshold = getConfig().getInt("guard.mspt-check.danger-threshold");
    }
}
