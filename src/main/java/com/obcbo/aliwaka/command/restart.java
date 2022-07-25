package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Aliwaka;
import com.obcbo.aliwaka.until.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class restart {
    public static boolean core(CommandSender sender) {
        if (!Permission.check(sender)) {
            sender.sendMessage(Aliwaka.prefix + "§c没有权限");
            return true;
        }
        sender.sendMessage(Aliwaka.prefix + "还没做完");
        return true;
    }
}
