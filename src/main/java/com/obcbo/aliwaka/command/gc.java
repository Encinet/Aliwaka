package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.file.Config;
import com.obcbo.aliwaka.file.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import static com.obcbo.aliwaka.file.Message.*;

public class gc {
    static long cooltime = 0; // 冷却起始
    public static boolean core(CommandSender sender) {
        if (!sender.hasPermission("aliwaka.admin")) {
            long time = System.currentTimeMillis() - cooltime;
            if (time < 10000) {
                sender.sendMessage(Message.prefix + CD);
                return true;
            } else {
                cooltime = System.currentTimeMillis();
            }
        }
        Bukkit.broadcast(Component.text(gcStart));
        long before = System.currentTimeMillis();
        System.gc();
        long total = System.currentTimeMillis() - before;
        Bukkit.broadcast(Component.text(gcEnd.replace("%time%", Long.toString(total))));
        return true;
    }
}
