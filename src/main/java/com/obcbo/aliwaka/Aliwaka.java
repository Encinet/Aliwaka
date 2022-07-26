package com.obcbo.aliwaka;

import com.obcbo.aliwaka.task.Guard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Aliwaka extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("MAIN > 开始加载");
        saveDefaultConfig();
        reloadConfig();
        if (Bukkit.getPluginCommand("aliwaka") != null) {
            Bukkit.getPluginCommand("aliwaka").setExecutor(new CommandManage());
        }
        getLogger().info("COMMAND > 命令注册完毕");
        Bukkit.getScheduler().runTask(this, Guard::start);
        getLogger().info("TASK > 任务开始加载");
        getLogger().info("MAIN > 成功启用插件");
    }

    @Override
    public void onDisable() {
        Guard.stop();
        getLogger().info("Thx Using, See you next time~");
    }
}
