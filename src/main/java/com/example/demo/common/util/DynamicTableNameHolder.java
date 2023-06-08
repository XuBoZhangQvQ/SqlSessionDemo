package com.example.demo.common.util;

import org.springframework.stereotype.Component;

/**
 * @author ZhangXuBo
 * @date 2022/5/10 11:04
 */
@Component
public class DynamicTableNameHolder {
    private static final ThreadLocal<String> TABLE_NAME_HOLDER = new ThreadLocal<>();

    public static String get() {
        return TABLE_NAME_HOLDER.get();
    }

    public static void set(String tableName) {
        TABLE_NAME_HOLDER.set(tableName);
    }

    public static void remove() {
        TABLE_NAME_HOLDER.remove();
    }
}
