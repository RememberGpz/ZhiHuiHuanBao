package com.lee.zhihuihuanbao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lee.zhihuihuanbao.app.MyApp;

import java.util.List;
import java.util.Set;

/**
 * @Author Lee
 * @Time 2017/9/5
 * @Theme
 */

public class SPUtil {

    private static String userInformation;

    private static Context getContext() {
        return MyApp.getInstance();
    }

    public static String getString(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString(key, "");
    }

    public static void putString(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString(key, value).commit();
    }

    public static void putInt(String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt(key, value).commit();
    }

    public static Set<String> getStringSet(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet(key, null);
    }

    public static void putStringSet(String key, Set<String> set) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putStringSet(key, set).commit();
    }

    public static boolean getBoolean(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(key, value).commit();
    }

    public static int getInt(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(key, 0);
    }

    public static float getFloat(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getFloat(key, -1f);
    }

    public static void putFloat(String key, float value) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putFloat(key, value).commit();
    }

    public static long getLong(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getLong(key, -1);
    }

    public static void putLong(String key, long value) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putLong(key, value).commit();
    }


    /**
     * 存储List<String>
     *
     * @param key     List<String>对应的key
     * @param strList 对应需要存储的List<String>
     */
    public static void putStrListValue(String key, List<String> strList) {
        if (null == strList) {
            return;
        }
        // 保存之前先清理已经存在的数据，保证数据的唯一性
        removeStrList(key);
        int size = strList.size();
        putInt(key + "size", size);
        for (int i = 0; i < size; i++) {
            putString(key + i, strList.get(i));
        }
    }

    /**
     * 清空List<String>所有数据
     *
     * @param key     List<String>对应的key
     */
    public static void removeStrList(String key) {
        int size = getInt(key + "size");
        if (0 == size) {
            return;
        }
        remove(key + "size");
        for (int i = 0; i < size; i++) {
            remove(key + i);
        }
    }



    /**
     * 清空对应key数据
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.remove(key).apply();
    }

}
