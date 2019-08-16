package com.xiaoma.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoma.job.entity.User;

public interface UserService extends IService<User> {

    int checkUserExist(String username);

    int checkPhoneExist(String username);

    User login(User user);
}
