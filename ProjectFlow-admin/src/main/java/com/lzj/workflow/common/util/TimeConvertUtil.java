package com.lzj.workflow.common.util;


public class TimeConvertUtil {

    /**
     * 将毫秒数转换为格式化的时间字符串
     *
     * @param milliseconds 毫秒数
     * @return 格式化的时间字符串
     */
    public static String format(long milliseconds) {
        if (milliseconds < 1000) {
            return milliseconds + "毫秒";
        }

        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        StringBuilder sb = new StringBuilder();

        appendIfPositive(sb, days, "天");
        appendIfPositive(sb, hours, "小时");
        appendIfPositive(sb, minutes, "分钟");
        appendIfPositive(sb, seconds, "秒");

        return sb.toString().trim();
    }

    private static void appendIfPositive(StringBuilder sb, long value, String unit) {
        if (value > 0) {
            sb.append(" ").append(value).append(unit);
        }
    }
}
