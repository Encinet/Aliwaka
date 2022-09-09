package com.obcbo.aliwaka.command;

import com.obcbo.aliwaka.file.Message;
import com.obcbo.aliwaka.until.Permissions;

import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.obcbo.aliwaka.file.Message.*;

public class shell implements Runnable {
    public static String text;
    public static CommandSender sender;

    public static boolean core(CommandSender sender, String[] args) {
        if (Permissions.check(sender, "aliwaka.admin")) {
            String text = String.join(" ", args);
            if (text.length() < 7) {
                sender.sendMessage(Message.prefix + notComplete);
                return true;
            }
            shell.text = text.substring(6);
            shell.sender = sender;
            sender.sendMessage(Message.prefix + shellStart.replace("%command%", shell.text));
            new Thread(new shell(), "Aliwaka-Shell").start();
        } else {
            sender.sendMessage(Message.prefix + noPermission);
        }
        return true;
    }

    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(runtime.exec(text).getInputStream()))) {
            String line;
            StringBuilder b = new StringBuilder();
            while ((line = br.readLine()) != null) {
                b.append(line).append("\n");
            }
            sender.sendMessage(b.toString());
        } catch (Exception e) {
            sender.sendMessage(Message.prefix + shellError.replace("%command%", shell.text));
            e.printStackTrace();
        }
    }
}
