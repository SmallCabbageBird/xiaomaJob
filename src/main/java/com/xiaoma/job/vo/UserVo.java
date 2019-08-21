package com.xiaoma.job.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户请求对象
 */
@Data
public class UserVo implements Serializable {


    @ApiModelProperty(name = "username",value = "用户名",example = "w131938192",dataType = "String",required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;//用户名

    @ApiModelProperty(name = "password", value = "密码",example = "wj13129",dataType = "String", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;//密码

    @ApiModelProperty(name = "code",value = "验证码",example = "3xbA",dataType = "String",required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;//验证码

    @ApiModelProperty(name = "codeKey",value = "验证码的key",example = "32189312893103daSA",dataType = "String",required = true)
    @NotBlank(message = "codeKey不能为空")
    private String codeKey;//验证码

    @ApiModelProperty(name = "phoneCode",value = "手机验证码",example = "562311",dataType = "String",required = true)
    @NotBlank(message = "手机验证码不能为空")
    private String phoneCode;//手机验证码



    @ApiModelProperty(name = "name",value = "用户姓名",example = "张三",dataType = "String")
    private String name;//姓名

    @ApiModelProperty(name = "sex",value = "性别",example = "男",dataType = "String")
    private String sex;//性别

    @ApiModelProperty(name = "age",value = "年龄",example = "18",dataType = "Integer")
    private Integer age;//年龄

    @ApiModelProperty(name = "birthday",value = "出生日期",example = "2019-08-19",dataType = "Date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;//出生日期

    @ApiModelProperty(name = "phone",value = "手机号码",example = "18578644444",dataType = "String")
    @NotBlank(message = "手机号码不能为空")
    private String phone;//手机号码

    @ApiModelProperty(name = "addressId",value = "地址id",example = "11/22/33",dataType = "String")
    private String addressId;//地址ID

    @ApiModelProperty(name = "address",value = "地址",example = "广东省/广州市/天河区",dataType = "String")
    private String address;//地址

    @ApiModelProperty(name = "fullAddress",value = "详细地址",example = "车陂南车陂小学",dataType = "String")
    private String fullAddress;//详细地址

}
