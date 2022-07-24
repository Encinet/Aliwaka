package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Aliwaka;
import com.obcbo.aliwaka.task.Guard;
import com.obcbo.aliwaka.until.OutputUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class info {
    public static boolean core(CommandSender sender) {
        sender.sendMessage(Aliwaka.prefix + "开始输出服务器信息");
        sender.sendMessage("§b服务器版本§7:§f 1.16.5");;
        sender.sendMessage("§b危险值§7:§f " + Guard.value);

        long max = Runtime.getRuntime().maxMemory();
        long use = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        sender.sendMessage("§b内存§7:§f " + String.format("%.2f%%", use / (double) max * 100) + " (" + use + "/" + max + ")");
        sender.sendMessage(String.format("§b在线玩家§7:§f §f%d/%d", Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));

        DecimalFormat df = new DecimalFormat("#.00");// 保留小数点后两位
        List<String> tps =new ArrayList<>(4);// tps值有4个
        for (@NotNull double single : Bukkit.getTPS()) {
            tps.add(df.format(single));
        }
        sender.sendMessage("§bTPS§7:§f " + tps);

        final int threadcount = Thread.currentThread().getThreadGroup().activeCount();
        sender.sendMessage("§b线程数§7:§f" + threadcount);

        sender.sendMessage("§b内存§7:§f " + OutputUtils.tanByte(max) + "§7-§f" + OutputUtils.tanByte(use) + "§7=§f" + OutputUtils.tanByte(max - use) + "  §b已分配§7:§f" + OutputUtils.tanByte(Runtime.getRuntime().totalMemory()));

        Server ser = Bukkit.getServer();
        final long dt = ser.getWorldContainer().getTotalSpace();
        final long du = ser.getWorldContainer().getUsableSpace();
        final long duse = dt - du;
        sender.sendMessage("§b磁盘§7:§f " + OutputUtils.tanByte(dt) + "§7-§f" + OutputUtils.tanByte(duse) + "§7=§f" + OutputUtils.tanByte(du));
        return true;
    }
}
