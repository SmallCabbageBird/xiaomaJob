package com.xiaoma.job.log;


import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {

    /** 模块 **/
    String title() default "";

    /** 功能 **/
    String action() default "";

}
