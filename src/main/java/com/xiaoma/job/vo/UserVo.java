package com.xiaoma.job.vo;

import com.xiaoma.job.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends User {

    private String vCode;//手机验证码

    private String code;//验证码
}
