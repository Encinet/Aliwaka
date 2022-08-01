package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.file.Message;
import com.obcbo.aliwaka.task.Guard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.obcbo.aliwaka.file.Config.*;
import static com.obcbo.aliwaka.file.Message.*;

public class info {
    public static boolean core(CommandSender sender) {
        sender.sendMessage(Message.prefix + "开始输出服务器信息");

        sender.sendMessage("§6服务器版本§7:§f " + Bukkit.getVersion() + String.format(" §6在线玩家§7:§f §f%d/%d", Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));
        sender.sendMessage("§6危险值§7:§f " + Guard.getWarn());

        long max = Runtime.getRuntime().maxMemory();
        long use = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        sender.sendMessage("§6内存§7:§f " + String.format("%.2f%%", use / (double) max * 100) + " (" + unitByte(max) + "§7-§f" + unitByte(use) + "§7=§f" + unitByte(max - use) + " 分配§7:§f" + unitByte(Runtime.getRuntime().totalMemory()) + ")");

        DecimalFormat df = new DecimalFormat("#.00");// 保留小数点后两位
        List<String> tps = new ArrayList<>(4);// tps值有4个
        for (double single : Bukkit.getTPS()) {
            StringBuilder stps = new StringBuilder();
            double dtps = Double.parseDouble(df.format(single));
            if (dtps < tpsDangerThreshold) {
                stps.append(colorDanger);
            } else if (dtps < tpsWarnThreshold) {
                stps.append(colorWarn);
            } else if (dtps >= tpsWarnThreshold) {
                stps.append(colorNormal);
            }
            stps.append(df.format(single));
            stps.append("§f");
            tps.add(String.valueOf(stps));
        }
        double dmspt = Double.parseDouble(df.format(Bukkit.getTickTimes()[0] / 1000000));
        StringBuilder mspt = new StringBuilder();
        if (dmspt > msptDangerThreshold) {
            mspt.append(colorDanger);
        } else if (dmspt < msptDangerThreshold && dmspt > 50) {
            mspt.append(colorWarn);
        } else if (dmspt <= 50) {
            mspt.append(colorNormal);
        }
        mspt.append(dmspt);
        sender.sendMessage("§6TPS§7:§f " + tps + " §6MSPT§7:§f " + mspt);

        sender.sendMessage("§6线程数§7:§f " + Thread.currentThread().getThreadGroup().activeCount());

        final long dt = Bukkit.getServer().getWorldContainer().getTotalSpace();
        final long du = Bukkit.getServer().getWorldContainer().getUsableSpace();
        final long duse = dt - du;
        sender.sendMessage("§6磁盘§7:§f " + unitByte(dt) + "§7-§f" + unitByte(duse) + "§7=§f" + unitByte(du));
        return true;
    }

    public static String unitByte(long enter) {
        if (enter < 0) return "NaN";
        final double standard = 1024D;
        final DecimalFormat df = new DecimalFormat("#.00");
        if (enter <= standard) {
            return df.format(enter) + "B";
        }
        final double m = standard * standard;
        if (enter <= m) {
            return df.format(enter / standard) + "KB";
        }
        final double g = m * standard;
        if (enter <= g) {
            return df.format(enter / m) + "MB";
        }
        return df.format(enter / g) + "GB";
    }
}
