package com.xiaoma.job.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *接口返回结果对象
 */
@Getter
@Setter
@ToString
public class Result implements Serializable {

    //业务状态码
    private Integer code;

    //响应信息
    private String msg;

    //响应数据
    private Object data;

    public Result(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
