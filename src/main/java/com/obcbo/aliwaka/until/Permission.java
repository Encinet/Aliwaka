package com.obcbo.aliwaka.until;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Permission {
    public static boolean check(CommandSender sender) {
        if (!sender.hasPermission("aliwaka.admin") && sender instanceof Player) {
            return false;
        } else {
            return true;
        }
    }
}
