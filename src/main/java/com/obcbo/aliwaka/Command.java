package com.obcbo.aliwaka;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.obcbo.aliwaka.until.OutputUtils;

import java.util.Locale;

public class Command implements @Nullable CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length<1) {
            Bukkit.getLogger().info("[Aliwaka] Ooops! 某只ObcbO还没写好帮助");
            return true;
        }
        switch (args[0]) {
            case ("gc"):
                Bukkit.getLogger().info("[Aliwaka] Start GC");
                System.gc();
                Bukkit.getLogger().info("[Aliwaka] GC execution is complete");
                return true;
            case ("info"):
                return getinfo();
            default:
                Bukkit.getLogger().info("[Aliwaka] Ooops! 某只ObcbO还没写好帮助");
                return true;
        }
    }

    private boolean getinfo() {
        Bukkit.getLogger().info("[Aliwaka] 服务器版本: 1.16.5");

        long max = Runtime.getRuntime().maxMemory();
        long use = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        Bukkit.getLogger().info("§b内存§3:§f " + String.format("%.2f%%", use / (double) max * 100) + String.format(" §b在线玩家§f%d/%d", Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));

        Server ser = Bukkit.getServer();
        final int threadcount = Thread.currentThread().getThreadGroup().activeCount();
        Bukkit.getLogger().info("§bTPS§3:§r " + Bukkit.getTPS() + "  §3线程数:§f" + threadcount);

        Bukkit.getLogger().info("§b内存剩余§3:§f " + OutputUtils.tanByte(max) + " §7- §f" + OutputUtils.tanByte(use) + " §7= §f" + OutputUtils.tanByte(max - use) + "  §b已分配: §f" + OutputUtils.tanByte(Runtime.getRuntime().totalMemory()));
        final long dt = ser.getWorldContainer().getTotalSpace();
        final long du = ser.getWorldContainer().getUsableSpace();
        final long duse = dt - du;
        Bukkit.getLogger().info("§b磁盘§3:§f " + OutputUtils.tanByte(dt) + " §7- §f" + OutputUtils.tanByte(duse) + " §7= §f" + OutputUtils.tanByte(du));
        return true;
    }
}
