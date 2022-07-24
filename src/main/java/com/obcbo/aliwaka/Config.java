package com.obcbo.aliwaka;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static me.dreamvoid.miraimc.bukkit.BukkitConfig.reloadConfig;

public class Config {
    public static @NotNull String getConfigString(String post){
        Plugin config = JavaPlugin.getProvidingPlugin(Aliwaka.class);
        return config.getConfig().getString(post);
    }
    public static @NotNull Long getConfigLong(String post){
        Plugin config = JavaPlugin.getProvidingPlugin(Aliwaka.class);
        return config.getConfig().getLong(post);
    }

    public void reload() {
        try {
            reloadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
