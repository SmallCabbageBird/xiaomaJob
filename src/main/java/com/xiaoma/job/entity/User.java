package com.xiaoma.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Common implements Serializable {
    @TableId(type = IdType.UUID)
    private String uid;//用户id

    private String username;//用户名

    @JsonIgnore
    private String password;//密码

    private String name;//姓名

    private String sex;//性别

    private Integer age;//年龄

    private Date birthday;//生日

    private String phone;//手机

    private String addressId;//地址ID

    private String address;//地址

    private String fullAddress;//详细地址

    private Integer isVip;//是否Vip 0否 1是

    private Long integral;//积分




    @JsonIgnore
    private int isDelete;

    @JsonIgnore
    private Integer disabled;//是否禁用账号 0禁用 1不禁用
}
