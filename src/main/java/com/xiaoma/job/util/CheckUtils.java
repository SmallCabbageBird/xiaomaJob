package com.xiaoma.job.util;

import com.xiaoma.job.Excption.CheckExcption;

public class CheckUtils {

    public static void check(Boolean flag,String msg) throws CheckExcption {
        if(!flag){
            throw new CheckExcption(msg);
        }
    }

    public static void checkNot(Boolean flag,String msg) throws CheckExcption {
        if(flag){
            throw new CheckExcption(msg);
        }
    }
}
