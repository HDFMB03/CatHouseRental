package com.Cathouse.context;

import java.util.HashMap;
import java.util.Map;

public class LocalThreadHolder {

    private static final ThreadLocal<Map<String, Integer>> USER_HOLDER = new ThreadLocal<>();

    public static void setUserId(Integer userId, Integer role) {
        Map<String, Integer> map = new HashMap<>();
        map.put("userId", userId);
        map.put("role", role);
        USER_HOLDER.set(map);
    }
    public static Integer getUserId() {
        return USER_HOLDER.get().get("userId");
    }
    public static Integer getRoleId() {
        return USER_HOLDER.get().get("role");
    }
    public static void clear() {
        USER_HOLDER.remove();
    }

}
