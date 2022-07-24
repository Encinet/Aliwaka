package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Aliwaka;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class gc {
    public static boolean core(CommandSender sender) {
        Bukkit.broadcastMessage("服务器开始强制回收内存,可能会有短暂卡顿");
        sender.sendMessage(Aliwaka.prefix + "开始GC回收");
        long before = System.currentTimeMillis();
        System.gc();
        long total = System.currentTimeMillis() - before;
        sender.sendMessage(Aliwaka.prefix + "GC任务完成 耗时" + total + "ms");
        Bukkit.broadcastMessage("服务器内存回收完成 耗时" + total + "ms");
        return true;
    }
}
