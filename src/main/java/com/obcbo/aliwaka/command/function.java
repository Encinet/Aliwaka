package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Config;
import com.obcbo.aliwaka.task.AntiCR.PointsChecker;
import com.obcbo.aliwaka.task.Guard;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.obcbo.aliwaka.task.AntiCR.CountChunk.playerPoints;

public class function {
    public static boolean core(CommandSender sender, String[] args) {
        if (!sender.hasPermission("aliwaka.admin")) {
            sender.sendMessage(Config.prefix + "§c没有权限");
            return true;
        } else if (args.length < 3) {
            sender.sendMessage(Config.prefix + "你似乎还没有输入完");
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

                    List<Map.Entry<String, Integer>> list = new ArrayList<>(playerPoints.entrySet()); //转换为list
                    list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

                    int num = 1;
                    for (Map.Entry<String, Integer> n : list) {
                        // key entry.getKey() ; value entry.getValue()
                        sender.sendMessage("§6" + num + ".§f" + n.getKey() + " §e" + n.getValue());
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
