package com.xiaoma.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User extends Common {

    @TableId(type = IdType.UUID)
    private String uid;//用户id

    private String username;//用户名

    private String password;//密码

    private String name;//姓名

    private String sex;//性别

    private Integer age;//年龄

    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone="GMT+8")
    private Date birthday;//生日

    private String phone;//手机

    private String addressId;//地址ID

    private String address;//地址

    private String fullAddress;//详细地址

    private Integer isVip;//是否Vip 0否 1是

    private Long integral;//积分

    private int isDelete;

    private Integer disabled;//是否禁用账号 0禁用 1不禁用
}
