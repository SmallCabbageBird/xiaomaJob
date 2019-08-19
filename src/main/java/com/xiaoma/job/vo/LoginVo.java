package com.xiaoma.job.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录请求对象
 */
@Data
public class LoginVo implements Serializable {

    @ApiModelProperty(name = "username",value = "用户名",example = "w131938192",dataType = "String",required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;//用户名

    @ApiModelProperty(name = "password", value = "密码",example = "wj13129",dataType = "String", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;//密码

    @ApiModelProperty(name = "code",value = "验证码",example = "3xbA",dataType = "String",required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;//验证码
}
