package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Aliwaka;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class restart {
    public static boolean core(CommandSender sender) {
        if (!sender.hasPermission("aliwaka.admin")) {
            sender.sendMessage(Aliwaka.prefix + "§c没有权限");
            return true;
        }
        sender.sendMessage(Aliwaka.prefix + "还没做完");
        return true;
    }
}
