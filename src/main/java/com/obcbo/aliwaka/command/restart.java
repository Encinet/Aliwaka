package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.Config;
import com.obcbo.aliwaka.until.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class restart {
    static ZoneId timezone = ZoneId.of(Objects.requireNonNull(Config.getConfig().getString("placeholders.time-zone")));
    public static boolean core(CommandSender sender, String[] args) {
        if (!Permission.check(sender)) {
            sender.sendMessage(Config.prefix + "§c没有权限");
            return true;
        }
        OffsetDateTime odt = OffsetDateTime.ofInstant(Instant.now(), timezone);
        String date = odt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String time = odt.format(DateTimeFormatter.ISO_LOCAL_TIME);
        sender.sendMessage(Config.prefix + date + " " + time);
        switch (args[1]) {
            case ("now"):
                Bukkit.savePlayers();
                Bukkit.shutdown();
                return true;
            case ("countdown"):
                return true;
            case ("timing"):
                return true;
            case ("time"):
                return true;
            default:
                sender.sendMessage(Config.prefix + "错误的命令语法");
                return true;
        }
    }
}
