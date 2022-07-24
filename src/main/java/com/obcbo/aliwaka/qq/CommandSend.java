package com.obcbo.aliwaka.qq;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandSend implements @Nullable CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            Bukkit.getLogger().info("[Aliwaka] Ooops! 没有发现要发送到QQ群的消息呢");
            return true;
        }
        SendMessage.sendGroup(args[0]);
        return true;
    }
}
