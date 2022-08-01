package com.obcbo.aliwaka;

import com.obcbo.aliwaka.task.AntiCR.CountChunk;
import com.obcbo.aliwaka.task.AntiCR.PointsChecker;
import com.obcbo.aliwaka.task.Guard;
import com.obcbo.aliwaka.until.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class Aliwaka extends JavaPlugin {
    public static final Logger logger = Logger.getLogger("Aliwaka");

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 15979);// bstats统计
        logger.info("MAIN > 开始加载");
        saveDefaultConfig();
        reloadConfig();
        Config.load();

        Bukkit.getPluginManager().registerEvents(new CountChunk(), this);
        logger.info("LISTENER > 监听器注册完毕");

        if (Bukkit.getPluginCommand("aliwaka") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("aliwaka")).setExecutor(new CommandManage());
        }
        logger.info("COMMAND > 命令注册完毕");

        Bukkit.getScheduler().runTask(this, PointsChecker::start);
        Bukkit.getScheduler().runTask(this, Guard::start);
        logger.info("TASK > 任务开始加载");
        logger.info("MAIN > 成功启用插件");
    }

    @Override
    public void onDisable() {
        PointsChecker.stop();
        Guard.stop();
        logger.info("感谢使用 期待下次相见");
    }

    public static void reload() {
        PointsChecker.stop();
        Guard.stop();
        Config.load();
        PointsChecker.start();
        Guard.start();
        logger.info("重载完成");
    }
}
