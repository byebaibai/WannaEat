package com.homework.getfood.util;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.homework.getfood.context.AppContext;

public class IconFetcher {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getIcon(int id){
        return AppContext.getContext().getDrawable(id);
    }
}