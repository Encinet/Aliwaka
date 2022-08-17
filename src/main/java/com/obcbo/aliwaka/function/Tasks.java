package com.obcbo.aliwaka.function;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;

import static com.cronutils.model.CronType.QUARTZ;

public class Tasks implements Runnable {
    public static HashMap<String, List<Object>> tasks = new HashMap<>();
    private static final CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));
    private static final Configuration config = YamlConfiguration.loadConfiguration(new File("tasks.yml"));
    public static Thread scheduler = new Thread(new Guard(), "Aliwaka-Scheduler");
    private static boolean on = true;

    public static void load() {
        List<String> names = config.getStringList("tasks");
        for (String name : names) {
            if (config.getBoolean("tasks." + name + ".enable", false)) {// 判断是否开启
                Cron cron = parser.parse(config.getString("tasks." + name + ".corn"));
                List<Object> value = new ArrayList<>();
                value.add(ExecutionTime.forCron(cron));// 获取corn
                value.add(config.getStringList("tasks." + name + ".command"));
                tasks.put(name, value);
                scheduler.start();
            }
        }
    }

    private static void start() {
        if (scheduler.isAlive()) {
            return;
        }
        scheduler = new Thread(new Guard(), "Aliwaka-Scheduler");
        on = true;
        scheduler.start();
    }

    @Override
    public void run() {
        while (on) {
            List<Long> num = new ArrayList<>();
            // 时间
            ZonedDateTime now = ZonedDateTime.now();
            for (Map.Entry<String, List<Object>> entry : tasks.entrySet()) {
                // key entry.getKey() ; value entry.getValue()
                ExecutionTime executionTime = (ExecutionTime) entry.getValue().get(0);
                Optional<Duration> timeToNextExecution = executionTime.timeToNextExecution(now);
                timeToNextExecution.toString();

            }
        }
    }
}
