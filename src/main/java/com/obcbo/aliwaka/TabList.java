package com.obcbo.aliwaka;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

enum TabList {
    FIRST(Arrays.asList("gc", "function", "help", "info", "reload", "restart", "shell"), 0, null, new int[]{1}),
    RESTART(Arrays.asList("countdown", "now", "time", "timing"), 1, "restart", new int[]{2}),
    FUNCTION(Arrays.asList("guard", "None"), 1, "function", new int[]{2}),
    GUARD(Arrays.asList("start", "stop"), 2, "guard", new int[]{3}),
    ;

    private final List<String> list;//返回的List
    private final int[] num;//这个参数可以出现的位置
    private final int befPos;//识别的上个参数的位置
    private final String bef;//上个参数的内容

    TabList(List<String> list, int befPos, String bef, int[] num) {
        this.list = list;
        this.befPos = befPos;
        this.bef = bef;
        this.num = num.clone();
    }

    public int[] getNum() {
        return num;
    }

    public String getBef() {
        return bef;
    }

    public List<String> getList() {
        return list;
    }

    public int getBefPos() {
        return befPos;
    }

    public static List<String> returnList(String[] Para, int curNum, CommandSender sender) {
        for (TabList tab : TabList.values()) {
            if (tab.getBefPos() - 1 >= Para.length) {
                continue;
            }
            if ((tab.getBef() == null || tab.getBef().equalsIgnoreCase(Para[tab.getBefPos() - 1])) && Arrays.binarySearch(tab.getNum(), curNum) >= 0) {
                return tab.getList();
            }
        }
        return null;
    }
}
