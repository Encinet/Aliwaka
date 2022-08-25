package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.file.Config;
import com.obcbo.aliwaka.file.Message;
import com.obcbo.aliwaka.function.Guard;
import org.bukkit.command.CommandSender;

import static com.obcbo.aliwaka.file.Message.*;

public class gc {
    static long coolTime = 0; // 冷却起始

    public static boolean core(CommandSender sender) {
        if (!sender.hasPermission("aliwaka.admin")) {
            long time = System.currentTimeMillis() - coolTime;
            if (time < Config.CD) {
                sender.sendMessage(Message.prefix + CD);
                return true;
            } else {
                coolTime = System.currentTimeMillis();
            }
        }
        Guard.gc();
        sender.sendMessage(prefix + "GC执行完毕");
        return true;
    }
}
