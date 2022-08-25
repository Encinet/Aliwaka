package com.obcbo.aliwaka.file;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.obcbo.aliwaka.Aliwaka.jp;
import static com.obcbo.aliwaka.Aliwaka.logger;

public class Message {
    public static FileConfiguration message = YamlConfiguration.loadConfiguration(
            new File(jp.getDataFolder(), "message.yml"));

    public static String prefix;
    public static String colorNormal;
    public static String colorWarn;
    public static String colorDanger;
    public static List<String> help;
    public static String reload;
    public static String noPermission;
    public static String CD;
    public static String notComplete;
    public static String wrongCommand;
    public static String functionDisable;
    public static String gcStart;
    public static String gcEnd;
    public static String crOutput;
    public static String crLimit;
    public static String crLimitLifted;
    public static String crTitle;
    public static String crSubtitle;
    public static String shellStart;
    public static String shellError;

    public static void load() {
        if (!(message.getInt("version") == 3)) {
            logger.warning("消息文件message.yml是旧版");
        }
        prefix = get("prefix", "&8[&6Ali&ewaka&8]&r ");
        colorNormal = get("color.normal", "&a");
        colorWarn = get("color.warn", "&6");
        colorDanger = get("color.danger", "&c");
        help = listCP("help");
        reload = get("reload", "重载完成");
        noPermission = get("no-permission", "&c没有权限");
        CD = get("CD", "冷却中");
        notComplete = get("not-complete", "你似乎还没有输入完命令");
        wrongCommand = get("wrong-command", "错误的命令");
        functionDisable = get("function-disable", "此功能处于关闭状态");
        gcStart = get("GC.start", "服务器开始强制回收内存,可能会有短暂卡顿");
        gcEnd = get("GC.end", "服务器内存回收完成 耗时%time%ms");
        crLimit = get("AntiCR.limit", "暂时限制速度");
        crLimitLifted = get("AntiCR.limit-lifted", "恢复速度");
        crTitle = get("AntiCR.title", "&6旅行者 停下来休息一会吧");
        crSubtitle = get("AntiCR.subtitle", "跑图卡顿真的很严重");
        crOutput = get("AntiCR.output", "输出区块加载计数中(仅在线)");
        shellStart = get("Shell.execution", "开始执行 %command%");
        shellError = get("Shell.error", "执行命令遇到问题 请查看控制台报错");
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
