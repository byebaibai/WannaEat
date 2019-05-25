package com.homework.getfood.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.homework.getfood.context.AppContext;

import java.lang.reflect.Field;

public class IconFetcher extends Application {
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