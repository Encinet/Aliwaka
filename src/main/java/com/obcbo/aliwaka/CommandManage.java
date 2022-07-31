package com.obcbo.aliwaka;

import com.obcbo.aliwaka.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandManage implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1 || "help".equals(args[0])) {
            sender.sendMessage("§f----- §6Ali§ewaka 帮助 §f-----");
            sender.sendMessage("§6/aw §7gc §f- 回收内存");
            sender.sendMessage("§6/aw §7function <name> §e<start|stop> §f- 开启禁用守护线程");
            sender.sendMessage("§6/aw §7help §f- 查看帮助");
            sender.sendMessage("§6/aw §7info §f- 输出服务器信息");
            sender.sendMessage("§6/aw §7reload §f- 重载插件");
            sender.sendMessage("§6/aw §7shell §e<command> §f- 模拟终端执行命令");
            return true;
        }
        switch (args[0]) {
            case ("gc"):
                return gc.core(sender);
            case ("info"):
                return info.core(sender);
            case ("shell"):
                return shell.core(sender, args);
            case ("function"):
                return function.core(sender, args);
            case ("reload"):
                Aliwaka.reload();
                return true;
            default:
                sender.sendMessage(Config.prefix + "错误的命令语法");
                return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return TabList.returnList(args, args.length, sender);
    }
}
