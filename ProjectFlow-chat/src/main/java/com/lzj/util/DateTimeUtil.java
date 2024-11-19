package com.lzj.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    // 定义日期格式
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前时间并以指定格式返回
     * @return 格式化后的时间字符串
     */
    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMATTER);
    }
}
