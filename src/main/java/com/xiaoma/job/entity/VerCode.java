package com.xiaoma.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("VerCode")
public class VerCode extends Common{
    @TableId(type = IdType.UUID)
    private String vid;//验证码id

    private String type;//验证码类型

    private String code;//验证码

    private String phone;//用户id
}
