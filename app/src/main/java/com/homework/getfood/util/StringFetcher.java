package com.homework.getfood.util;

import com.homework.getfood.context.AppContext;

public class StringFetcher {
    public static String getString(int id){
        return AppContext.getContext().getString(id);
    }
}
