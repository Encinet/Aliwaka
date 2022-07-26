package com.obcbo.aliwaka;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    static Plugin config = JavaPlugin.getProvidingPlugin(Aliwaka.class);
    public static final String prefix = getConfig().getString("prefix");

    public static FileConfiguration getConfig() {
        return config.getConfig();
    }
}
