package com.obcbo.aliwaka;

import com.obcbo.aliwaka.file.Config;
import com.obcbo.aliwaka.file.Message;
import com.obcbo.aliwaka.function.AntiCR.CountChunk;
import com.obcbo.aliwaka.function.AntiCR.PointsChecker;
import com.obcbo.aliwaka.function.Guard;
import com.obcbo.aliwaka.until.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

import static com.obcbo.aliwaka.file.Config.crEnable;
import static com.obcbo.aliwaka.file.Config.guardEnable;
import static com.obcbo.aliwaka.file.Message.message;
import static com.obcbo.aliwaka.file.Message.reload;

public final class Aliwaka extends JavaPlugin {
    public static JavaPlugin jp;
    public static final Logger logger = Logger.getLogger("Aliwaka");

    @Override
    public void onEnable() {
        jp = JavaPlugin.getProvidingPlugin(Aliwaka.class);

        logger.info("MAIN > Loading");
        saveDefaultConfig();
        saveResource("message.yml", false);// false为不覆盖 true为每次调用都覆盖
        saveResource("tasks.yml", false);
        reloadConfig();
        Config.load();
        Message.load();
        // Tasks.load();

        Bukkit.getPluginManager().registerEvents(new CountChunk(), this);
        logger.info("LISTENER > Registered");

        if (Bukkit.getPluginCommand("aliwaka") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("aliwaka")).setExecutor(new CommandManage());
        }
        logger.info("COMMAND > Registered");

        Bukkit.getScheduler().runTask(this, () -> {
            if (crEnable) {
                PointsChecker.start();
            }
        });
        // Bukkit.getScheduler().runTask(this, Guard::start);
        Bukkit.getScheduler().runTask(this, () -> {
            if (guardEnable) {
                Guard.start();
            }
        });
        logger.info("TASK > Loading");

        new Metrics(this, 15979);// bstats统计

        logger.info("MAIN > Plugin enabled successfully");
    }

    @Override
    public void onDisable() {
        PointsChecker.stop();
        Guard.stop();
        logger.info("Thanks for using");
    }

    public static void reload(CommandSender sender) {
        PointsChecker.stop();
        Guard.stop();

        jp.reloadConfig();
        message = YamlConfiguration.loadConfiguration(
                new File(jp.getDataFolder(), "message.yml"));
        Config.load();
        Message.load();

        // Tasks.load();
        if (crEnable) {
            Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Aliwaka.class), () -> {
                PointsChecker.start();
            });
        }
        if (guardEnable) {
            Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Aliwaka.class), () -> {
                Guard.start();
            });
        }
        if (sender instanceof Player) {// 如果是玩家执行 在控制台也输出
            logger.info(reload);
        }
        sender.sendMessage(Message.prefix + reload);
    }
}
