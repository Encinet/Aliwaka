package com.obcbo.aliwaka;

import com.obcbo.aliwaka.task.Guard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Aliwaka extends JavaPlugin {
    public static final Logger logger = Logger.getLogger("Aliwaka");

    @Override
    public void onEnable() {
        logger.info("MAIN > 开始加载");
        saveDefaultConfig();
        reloadConfig();
        Config.load();
        if (Bukkit.getPluginCommand("aliwaka") != null) {
            Bukkit.getPluginCommand("aliwaka").setExecutor(new CommandManage());
        }
        logger.info("COMMAND > 命令注册完毕");
        Bukkit.getScheduler().runTask(this, Guard::start);
        logger.info("TASK > 任务开始加载");
        logger.info("MAIN > 成功启用插件");
    }

    @Override
    public void onDisable() {
        Guard.stop();
        logger.info("感谢使用 期待下次相见");
    }

    public static void reload() {
        Guard.stop();
        Config.load();
        Guard.start();
        logger.info("重载完成");
    }
}
