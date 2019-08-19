package com.xiaoma.job.util;

import com.xiaoma.job.pojo.Result;
import com.xiaoma.job.pojo.Status;


public class ResultUtils {


    private ResultUtils(){};

    public static Result ok(){
        return new Result(Status.SUCCESS.getCode(),Status.SUCCESS.getMsg());
    }


    public static Result ok(Object data){
        return new Result(Status.SUCCESS.getCode(),Status.SUCCESS.getMsg(),data);
    }

    public static Result ok(String msg,Object data){
        return new Result(Status.SUCCESS.getCode(),msg,data);
    }


    public static Result error(){
        return new Result(Status.ERROR.getCode(),Status.ERROR.getMsg());
    }

    public static Result error(String msg){
        return new Result(Status.ERROR.getCode(),msg);
    }


    public static Result error(String msg,Object data){
        return new Result(Status.ERROR.getCode(),msg,data);
    }
}
