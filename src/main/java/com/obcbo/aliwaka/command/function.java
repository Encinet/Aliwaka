package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Config;
import com.obcbo.aliwaka.task.AntiCR.PointsChecker;
import com.obcbo.aliwaka.task.Guard;
import org.bukkit.command.CommandSender;

import java.util.Map;

import static com.obcbo.aliwaka.task.AntiCR.AntiCR.playerPoints;

public class function {
    public static boolean core(CommandSender sender, String[] args) {
        if (!sender.hasPermission("aliwaka.admin")) {
            sender.sendMessage(Config.prefix + "§c没有权限");
            return true;
        }
        switch (args[1]) {
            case ("guard"):
                if ("start".equals(args[2])) {
                    Guard.start();
                } else if ("stop".equals(args[2])) {
                    Guard.stop();
                } else {
                    sender.sendMessage(Config.prefix + "状态仅支持start和stop");
                }
                break;
            case ("AntiCR"):
                if ("start".equals(args[2])) {
                    PointsChecker.start();
                } else if ("stop".equals(args[2])) {
                    PointsChecker.stop();
                } else if ("list".equals(args[2])) {
                    sender.sendMessage(Config.prefix + "输出区块加载计数中(仅在线)");
                    playerPoints.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByKey().reversed())
                            .forEachOrdered(e -> playerPoints.put(e.getKey(), e.getValue()));
                    int num = 1;
                    for (Map.Entry<String, Integer> entry : playerPoints.entrySet()) {
                        // key entry.getKey() ; value entry.getValue()
                        sender.sendMessage("§6" + num + ".§f" + entry.getKey() + " §e" + entry.getValue());
                        num++;
                    }
                } else {
                    sender.sendMessage(Config.prefix + "状态仅支持start和stop和list");
                }
                break;
            default:
                sender.sendMessage(Config.prefix + "不存在的功能");
                break;
        }
        return true;
    }
}
