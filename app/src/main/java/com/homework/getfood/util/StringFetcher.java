package com.homework.getfood.util;

import com.homework.getfood.context.AppContext;

/**
 * 根据String的Id返回String
 */
public class StringFetcher {
    /**
     * @param id String的ID
     * @return 该ID对应的String
     */
    public static String getString(int id){
        return AppContext.getContext().getString(id);
    }
}
