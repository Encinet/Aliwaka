package com.obcbo.aliwaka;

import com.obcbo.aliwaka.qq.CommandSend;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import com.obcbo.aliwaka.qq.event.FriendMessageReceiveEvent;
import com.obcbo.aliwaka.qq.event.GroupMessageReceiveEvent;

public final class Aliwaka extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("MAIN >  开始加载");
        saveDefaultConfig();
        reloadConfig();
        // Plugin startup logic
        if (Bukkit.getPluginCommand("aliwaka") != null) {
            Bukkit.getPluginCommand("aliwaka").setExecutor(new Command());
        }
        if (Bukkit.getPluginCommand("qq") != null) {
            Bukkit.getPluginCommand("qq").setExecutor(new CommandSend());
        }
        getLogger().info("COMMAND > 命令注册完毕");

        Bukkit.getPluginManager().registerEvents(new FriendMessageReceiveEvent(), this);
        getLogger().info("QQ > 监听器 FriendMessageReceiveEvent 注册完毕");
        Bukkit.getPluginManager().registerEvents(new GroupMessageReceiveEvent(), this);
        getLogger().info("QQ > 监听器 GroupMessageReceiveEvent 注册完毕");
        //Bukkit.getPluginManager().registerEvents(new GroupMemberJoinEvent(), this);
        //getLogger().info("QQ > 监听器 GroupMemberJoinEvent 注册完毕");
        //Bukkit.getPluginManager().registerEvents(new GroupMemberQuitEvent(), this);
        //getLogger().info("QQ > 监听器 GroupMemberQuitEvent 注册完毕");
        getLogger().info("QQ > 监听器注册完毕");
        // Papi变量注册
        // new PAPIHolder(this).register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Thx Using, See you next time~");
    }
}
