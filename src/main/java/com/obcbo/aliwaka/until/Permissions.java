package com.obcbo.aliwaka.until;

import org.bukkit.command.CommandSender;

public class Permissions {
    public static boolean check(CommandSender sender, String text) {
        if (sender.hasPermission(text)) {
            return true;
        } else {
            StringBuilder sb = new StringBuilder(text);
            sb.replace(text.lastIndexOf(".") + 1, text.length(), "*");
            if (sender.hasPermission(sb.toString())) {
                return true;
            }
        }
        return false;
    }
}
