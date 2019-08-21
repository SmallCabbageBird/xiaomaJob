package com.xiaoma.job.pojo;

public interface Message {
    String ERROR_USERNAMEORPASSWORD = "用户名或密码错误";
    String ERROR_USER_EXSIT = "用户已存在";
    String ERROR_USER_NOT_EXSIT = "用户不存在";
    String ERROR_CAOTCHA_KEY_ERROR = "验证码key错误";
    String ERROR_CAOTCHA_ERROR = "验证码错误";
    String ERROR_CAOTCHA_TIMEOUT = "验证码过期";
    String ERROR_CAOTCHA_NULL = "验证码为空";
    String ERROR_PHONE_CAOTCHA_ERROR = "手机验证码错误";
    String ERROR_PHONE_CAOTCHA_TIMEOUT = "手机验证码过期";
    String ERROR_PHONE_CAOTCHA_NULL = "手机验证码为空";
}
