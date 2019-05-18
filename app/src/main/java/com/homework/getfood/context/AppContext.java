package com.homework.getfood.context;

import android.app.Application;

public class AppContext extends Application {
    private static AppContext mInstance;

    public static AppContext getContext(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
