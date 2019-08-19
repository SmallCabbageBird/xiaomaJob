package com.xiaoma.job.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 验证码请求对象
 */
@Data
public class VerCodeVo implements Serializable {

    @ApiModelProperty(name = "type",value = "手机验证码类型:register注册,login登录,findPwd找回密码",example = "register",dataType = "String",required = true)
    @NotBlank
    private String type;//验证码类型

    @ApiModelProperty(name = "phone",value = "手机号码",example = "18578644444",dataType = "String",required = true)
    @NotBlank
    private String phone;//用户手机
}
