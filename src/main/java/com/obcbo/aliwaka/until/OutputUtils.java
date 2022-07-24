package com.obcbo.aliwaka.until;
import java.text.DecimalFormat;

public class OutputUtils {
    private final static long day = 86400000L; //一天
    private final static long hour = 3600000L; //一小时
    private final static long minute = 60000L; //一分钟
    private final static long second = 1000L; //一秒

    public static long getTimeMintoh(String str) {
        if (str.isEmpty()) return 0;
        try{
            char e = str.charAt(str.length() - 1);
            switch (e) {
                case 'S':
                case 's':
                    str = str.substring(0,str.length() - 1);
                    return Long.parseLong(str) * second;
                case 'M':
                case 'm':
                    str = str.substring(0,str.length() - 1);
                    return Long.parseLong(str) * minute;
                case 'H':
                case 'h':
                    str = str.substring(0,str.length() - 1);
                    return Long.parseLong(str) * hour;
                case 'D':
                case 'd':
                    str = str.substring(0,str.length() - 1);
                    return Long.parseLong(str) * day;
            }
            return Long.parseLong(str) * minute; //如果都不是默认当分钟处理
        }catch (NumberFormatException ex){
            return -1;
        }
    }

    public static long toByteLength(String str) {
        if (str == null || str.isEmpty()) return 0;
        char e = str.charAt(str.length() - 1);
        switch (e) {
            case 'k':
            case 'K':
                str = str.substring(0,str.length() - 1);
                return Long.parseLong(str) * 1024L;
            case 'm':
            case 'M':
                str = str.substring(0,str.length() - 1);
                return Long.parseLong(str) * 1048576L;
            case 'g':
            case 'G':
                str = str.substring(0,str.length() - 1);
                return Long.parseLong(str) * 1073741824L;
        }
        return Long.parseLong(str) * 1048576L;
    }

    public static String tanMintoh(long l) {
        final StringBuilder sb = new StringBuilder();
        if (l < 0) return String.valueOf(l);
        if (l < second){
            return sb.append(l).append("毫秒").toString();
        }
        if (l >= day){
            int i = 0;
            while (l >= day) {
                l -= day;
                i++;
            }
            sb.append(i).append("天");
        }
        if (l >= hour){
            int i = 0;
            while (l >= hour) {
                l -= hour;
                i++;
            }
            sb.append(i).append("小时");
        }
        if (l >= minute){
            int i = 0;
            while (l >= minute) {
                l -= minute;
                i++;
            }
            sb.append(i).append("分钟");
        }
        if (l >= second){
            int i = 0;
            while (l >= second) {
                l -= second;
                i++;
            }
            sb.append(i).append("秒");
        }
        return sb.toString();
    }

    public static String tanByte(long l) {
        if (l < 0) return "NaN";
        final double k = 1024D;
        final DecimalFormat df = new DecimalFormat("#.00");
        if (l <= k){
            return df.format(l) + "B";
        }
        final double m = k * k;
        if (l <= m){
            return df.format(l / k) + "KB";
        }
        final double g = m * k;
        if (l <= g){
            return df.format(l / m) + "MB";
        }
        return df.format(l / g) + "GB";
    }


}