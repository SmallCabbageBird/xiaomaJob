package com.xiaoma.job.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.job.mapper.UserMapper;
import com.xiaoma.job.entity.User;
import com.xiaoma.job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int checkUserExist(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return this.count(queryWrapper);
    }

    @Override
    public int checkPhoneExist(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone", phone);
        return this.count(queryWrapper);
    }

    @Override
    public User login(User user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername()).eq("password",user.getPassword());
        return this.getOne(queryWrapper);
    }

}
