package com.obcbo.aliwaka;

import com.obcbo.aliwaka.command.*;
import com.obcbo.aliwaka.task.Guard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandManage implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Config.prefix + "Ooops! 某只ObcbO还没写好帮助");
            return true;
        }
        switch (args[0]) {
            case ("gc"):
                return gc.core(sender);
            case ("info"):
                return info.core(sender);
            case ("restart"):
                return restart.core(sender, args);
            case ("shell"):
                return shell.core(sender, args);
            case ("guard"):
                switch (args[1]) {
                    case ("start"):
                        Guard.start();
                        return true;
                    case ("stop"):
                        Guard.stop();
                        return true;
                    default:
                        sender.sendMessage(Config.prefix + "错误的命令语法");
                        return true;
                }
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
