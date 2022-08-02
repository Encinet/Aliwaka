package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.file.Message;
import com.obcbo.aliwaka.task.AntiCR.PointsChecker;
import com.obcbo.aliwaka.task.Guard;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.obcbo.aliwaka.file.Config.crEnable;
import static com.obcbo.aliwaka.file.Message.*;
import static com.obcbo.aliwaka.task.AntiCR.CountChunk.playerPoints;

public class function {
    public static boolean core(CommandSender sender, String[] args) {
        if (!sender.hasPermission("aliwaka.admin")) {
            sender.sendMessage(Message.prefix + noPermission);
            return true;
        } else if (args.length < 3) {
            sender.sendMessage(Message.prefix + notComplete);
            return true;
        }
        switch (args[1]) {
            case ("guard"):
                if ("start".equals(args[2])) {
                    Guard.start();
                } else if ("stop".equals(args[2])) {
                    Guard.stop();
                } else {
                    sender.sendMessage(Message.prefix + wrongCommand);
                }
                break;
            case ("AntiCR"):
                if ("start".equals(args[2])) {
                    PointsChecker.start();
                } else if ("stop".equals(args[2])) {
                    PointsChecker.stop();
                } else if ("list".equals(args[2])) {
                    if (!crEnable) {// 功能关闭时执行
                        sender.sendMessage(Message.prefix + functionDisable);
                        return true;
                    }

                    sender.sendMessage(Message.prefix + crOutput);

                    List<Map.Entry<String, Integer>> list = new ArrayList<>(playerPoints.entrySet()); //转换为list
                    list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

                    int num = 1;
                    for (Map.Entry<String, Integer> n : list) {
                        // key entry.getKey() ; value entry.getValue()
                        sender.sendMessage("§6" + num + ".§f" + n.getKey() + " §e" + n.getValue());
                        num++;
                    }
                } else {
                    sender.sendMessage(Message.prefix + wrongCommand);
                }
                break;
            default:
                sender.sendMessage(Message.prefix + notComplete);
                break;
        }
        return true;
    }
}
