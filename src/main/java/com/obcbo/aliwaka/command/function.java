package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Config;
import com.obcbo.aliwaka.task.AntiCR.PointsChecker;
import com.obcbo.aliwaka.task.Guard;
import org.bukkit.command.CommandSender;

import static com.obcbo.aliwaka.task.AntiCR.AntiCR.playerPoints;

public class function {
    public static boolean core(CommandSender sender, String[] args) {
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
                    sender.sendMessage(Config.prefix + playerPoints);
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
