package com.obcbo.aliwaka.file;

import com.obcbo.aliwaka.Aliwaka;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Message {
    static FileConfiguration message = YamlConfiguration.loadConfiguration(
            new File(JavaPlugin.getProvidingPlugin(Aliwaka.class).getDataFolder(), "message.yml"));

    public static String prefix;
    public static String colorNormal;
    public static String colorWarn;
    public static String colorDanger;
    public static List<String> help;
    public static String noPermission;
    public static String CD;
    public static String gcStart;
    public static String gcEnd;
    public static String crLimit;
    public static String crLimitLifted;
    public static String crTitle;
    public static String crSubtitle;

    public static void load() {
        // ChatColor.translateAlternateColorCodes('&',消息内容)
        prefix = get("prefix", "&8[&6Ali&ewaka&8]&r ");
        colorNormal = get("color.normal", "&a");
        colorWarn = get("color.warn", "&6");
        colorDanger = get("color.danger", "&c");
        help = listCP("help");
        noPermission = get("no-permission", "&c没有权限");
        CD = get("CD", "冷却中");
        gcStart = get("GC.start", "服务器开始强制回收内存,可能会有短暂卡顿");
        gcEnd = get("GC.end", "服务器内存回收完成 耗时%time%ms");
        crLimit = get("AntiCR.limit", "暂时限制速度");
        crLimitLifted = get("AntiCR.limit-lifted", "恢复速度");
        crTitle = get("AntiCR.title", "&6旅行者 停下来休息一会吧");
        crSubtitle = get("AntiCR.subtitle", "跑图卡顿真的很严重");
    }

    private static String get(String path, String def) {
        return ChatColor.translateAlternateColorCodes('&', message.getString(path, def));
    }

    private static List<String> listCP(String path) {
        List<String> put = new ArrayList<>();
        for (String now : message.getStringList(path)) {
            put.add(ChatColor.translateAlternateColorCodes('&', now));
        }
        return put;
    }
}
