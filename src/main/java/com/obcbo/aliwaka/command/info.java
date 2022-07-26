package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Config;
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
        sender.sendMessage(Config.prefix + "开始输出服务器信息");
        sender.sendMessage("§b服务器版本§7:§f 1.16.5 " + String.format("§b在线玩家§7:§f §f%d/%d", Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));
        sender.sendMessage("§b危险值§7:§f " + Guard.getwarn());

        long max = Runtime.getRuntime().maxMemory();
        long use = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        sender.sendMessage("§b内存§7:§f " + String.format("%.2f%%", use / (double) max * 100) + " (" + OutputUtils.tanByte(max) + "§7-§f" + OutputUtils.tanByte(use) + "§7=§f" + OutputUtils.tanByte(max - use) + " 分配§7:§f" + OutputUtils.tanByte(Runtime.getRuntime().totalMemory()) +")");

        DecimalFormat df = new DecimalFormat("#.00");// 保留小数点后两位
        List<String> tps =new ArrayList<>(4);// tps值有4个
        for (@NotNull double single : Bukkit.getTPS()) {
            tps.add(df.format(single));
        }
        double mspt = Double.parseDouble(df.format(Bukkit.getTickTimes()[0] / 1000000));
        sender.sendMessage("§bTPS§7:§f " + tps + " §bMSPT§7:§f " + mspt);

        final int threadcount = Thread.currentThread().getThreadGroup().activeCount();
        sender.sendMessage("§b线程数§7:§f " + threadcount);

        Server ser = Bukkit.getServer();
        final long dt = ser.getWorldContainer().getTotalSpace();
        final long du = ser.getWorldContainer().getUsableSpace();
        final long duse = dt - du;
        sender.sendMessage("§b磁盘§7:§f " + OutputUtils.tanByte(dt) + "§7-§f" + OutputUtils.tanByte(duse) + "§7=§f" + OutputUtils.tanByte(du));
        return true;
    }
}
