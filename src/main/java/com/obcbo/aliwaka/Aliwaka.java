package com.obcbo.aliwaka;

import com.obcbo.aliwaka.file.Config;
import com.obcbo.aliwaka.file.Message;
import com.obcbo.aliwaka.task.AntiCR.CountChunk;
import com.obcbo.aliwaka.task.AntiCR.PointsChecker;
import com.obcbo.aliwaka.task.Guard;
import com.obcbo.aliwaka.until.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

import static com.obcbo.aliwaka.file.Config.crEnable;
import static com.obcbo.aliwaka.file.Config.guardEnable;

public final class Aliwaka extends JavaPlugin {
    public static final Logger logger = Logger.getLogger("Aliwaka");

    @Override
    public void onEnable() {
        new Metrics(this, 15979);// bstats统计
        logger.info("MAIN > 开始加载");
        saveDefaultConfig();
        saveResource("message.yml", false);// false为不覆盖 true为每次调用都覆盖
        reloadConfig();
        Config.load();
        Message.load();

        Bukkit.getPluginManager().registerEvents(new CountChunk(), this);
        logger.info("LISTENER > 监听器注册完毕");

        if (Bukkit.getPluginCommand("aliwaka") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("aliwaka")).setExecutor(new CommandManage());
        }
        logger.info("COMMAND > 命令注册完毕");

        Bukkit.getScheduler().runTask(this, () -> {
            if (crEnable) {
                PointsChecker.start();
            }
        });
        //Bukkit.getScheduler().runTask(this, Guard::start);
        Bukkit.getScheduler().runTask(this, () -> {
            if (guardEnable) {
                Guard.start();
            }
        });
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
        Message.load();
        if (crEnable) {
            PointsChecker.start();
        }
        if (guardEnable) {
            Guard.start();
        }
        logger.info("重载完成");
    }
}
