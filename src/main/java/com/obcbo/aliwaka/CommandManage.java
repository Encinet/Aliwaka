package com.obcbo.aliwaka;

import com.obcbo.aliwaka.command.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandManage implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Aliwaka.prefix + "Ooops! 某只ObcbO还没写好帮助");
            return true;
        }
        // 例子:
        // 将 [/aw a b c]
        // 转为 [a b c]
        String text = String.join(" ", args);
        switch (args[0]) {
            case ("gc"):
                return gc.core(sender);
            case ("info"):
                return info.core(sender);
            case ("restart"):
                return restart.core(sender);
            case ("shell"):
                return shell.core(sender, text);
            default:
                sender.sendMessage(Aliwaka.prefix + "Ooops! 某只ObcbO还没写好帮助");
                return true;
        }
    }
}
