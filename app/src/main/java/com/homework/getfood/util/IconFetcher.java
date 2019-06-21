package com.homework.getfood.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Field;

/**
 * 根据图片名称获得图片ID
 */
@SuppressLint("Registered")
public class IconFetcher extends Application {
    /**
     * 根据图片名称获得图片ID
     * @param variableName 名称
     * @param c 类别
     * @return ID
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

