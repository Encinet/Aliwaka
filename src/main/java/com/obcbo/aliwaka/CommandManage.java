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
        switch (args[0]) {
            case ("gc"):
                return gc.core(sender);
            case ("info"):
                return info.core(sender);
            case ("restart"):
                return restart.core(sender);
            default:
                sender.sendMessage(Aliwaka.prefix + "Ooops! 某只ObcbO还没写好帮助");
                return true;
        }
    }
}
