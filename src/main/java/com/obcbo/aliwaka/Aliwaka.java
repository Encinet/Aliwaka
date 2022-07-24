package com.obcbo.aliwaka;

import com.obcbo.aliwaka.task.Guard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Aliwaka extends JavaPlugin {
    public static final String prefix = "§8[§6Ali§ewaka§8]§r ";
    @Override
    public void onEnable() {
        getLogger().info("MAIN > 开始加载");
        saveDefaultConfig();
        reloadConfig();
        // Plugin startup logic
        if (Bukkit.getPluginCommand("aliwaka") != null) {
            Bukkit.getPluginCommand("aliwaka").setExecutor(new CommandManage());
        }
        getLogger().info("COMMAND > 命令注册完毕");
        getLogger().info("TASK > 任务开始加载");
        getLogger().info("MAIN > 成功启用插件");
        Bukkit.getScheduler().runTask(this,() -> {
            new Thread(new Guard(), "GuardThread").start();
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Thx Using, See you next time~");
    }
}
