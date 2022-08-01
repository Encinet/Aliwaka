package com.obcbo.aliwaka.file;

import com.obcbo.aliwaka.Aliwaka;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Message {
    static FileConfiguration message = YamlConfiguration.loadConfiguration(
            new File(JavaPlugin.getProvidingPlugin(Aliwaka.class).getDataFolder(), "message.yml"));

    public static String prefix;
    public static String colorNormal;
    public static String colorWarn;
    public static String colorDanger;
    public static List<String> help;

    public static void load() {
        // ChatColor.translateAlternateColorCodes('&',消息内容)
        prefix = ChatColor.translateAlternateColorCodes('&', message.getString("prefix", "&8[&6Ali&ewaka&8]&r "));
        colorNormal = ChatColor.translateAlternateColorCodes('&', message.getString("color.normal", "&a"));
        colorWarn = ChatColor.translateAlternateColorCodes('&', message.getString("color.warn", "&6"));
        colorDanger = ChatColor.translateAlternateColorCodes('&', message.getString("color.danger", "&c"));
        help = listCP("help");
    }

    public static List<String> listCP(String path) {
        List<String> put = new ArrayList<>();
        for (String now : message.getStringList(path)) {
            put.add(ChatColor.translateAlternateColorCodes('&', now));
        }
        return put;
    }
}
