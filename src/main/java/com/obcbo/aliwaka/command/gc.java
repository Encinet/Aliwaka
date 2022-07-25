package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Aliwaka;
import com.obcbo.aliwaka.until.Permission;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class gc {
    static long cooltime = 0; // 冷却起始
    public static boolean core(CommandSender sender) {
        if (!Permission.check(sender) ) {
            long time = System.currentTimeMillis() - cooltime;
            if (time < 10000) {
                sender.sendMessage(Aliwaka.prefix + "冷却中");
                return true;
            } else {
                cooltime = System.currentTimeMillis();
            }
        }
        Bukkit.broadcast(Component.text("服务器开始强制回收内存,可能会有短暂卡顿"));
        sender.sendMessage(Aliwaka.prefix + "开始GC回收");
        long before = System.currentTimeMillis();
        System.gc();
        long total = System.currentTimeMillis() - before;
        sender.sendMessage(Aliwaka.prefix + "GC任务完成 耗时" + total + "ms");
        Bukkit.broadcast(Component.text("服务器内存回收完成 耗时" + total + "ms"));
        return true;
    }
}
