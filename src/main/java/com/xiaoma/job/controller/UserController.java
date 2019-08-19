package com.xiaoma.job.controller;

import com.xiaoma.job.entity.VerCode;
import com.xiaoma.job.log.MyLog;
import com.xiaoma.job.pojo.Result;
import com.xiaoma.job.entity.User;
import com.xiaoma.job.service.UserService;
import com.xiaoma.job.service.VerCodeService;
import com.xiaoma.job.util.MD5Utils;
import com.xiaoma.job.util.ResultUtils;
import static com.xiaoma.job.util.CheckUtils.check;
import static com.xiaoma.job.util.CheckUtils.checkNot;

import com.xiaoma.job.vo.LoginVo;
import com.xiaoma.job.vo.UserVo;
import com.xiaoma.job.vo.VerCodeVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Validated
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
@Slf4j
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private VerCodeService verCodeService;

    @GetMapping("/login")
    @MyLog(title = "用户模块",action = "登录")
    @ApiOperation(value = "登录")
    public Result login(@Valid LoginVo loginVo) throws Exception{



        check(userService.checkUserExist(loginVo.getUsername())> 0,"用户不存在");
        /**
         *  TODO 验证验证码是否正确,还需写获取验证码的接口
         */


        /**
         *   TODO 还缺个密码前端加密后台解密的过程(防止用户名密码明文传输)
         */
        User user = new User();
        BeanUtils.copyProperties(loginVo,user);
        user.setPassword(MD5Utils.getStrrMD5(user.getPassword()));
        User dbUser = userService.login(user);
        return dbUser == null ? ResultUtils.error("用户名或密码错误")  : ResultUtils.ok(dbUser);
    }

    /**
     * 检查用户名是否已经被注册
     * @param username
     * @return
     */
    @GetMapping("/checkUser")
    @MyLog(title = "用户模块",action = "检查用户名是否已经被注册")
    @ApiOperation(value = "检查用户名是否已经被注册", notes = "检查用户名是否已经被注册")
    public Result checkUser(@ApiParam(name = "username", value = "用户名") @RequestParam String username){
        return  userService.checkUserExist(username) > 0 ? ResultUtils.error("用户已经存在") : ResultUtils.ok();
    }


    /**
     * 发送手机验证码
     * @param verCodeVo
     * @return
     */
    @PostMapping("/sendPhoneCode")
    @MyLog(title = "用户模块",action = "发送手机验证码")
    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码")
    public Result sendPhoneCode(@Valid VerCodeVo verCodeVo) throws Exception{
        String type = verCodeVo.getType();

        check(StringUtils.isNotBlank(type),"type不能为空");

        String vCode = "123456";
        /**
         * TODO 获取手机验证码对接短信平台
         */

        /**
         * TODO 把手机验证码存储到redis中(设置时效性为5分钟) 18578649851:[register:{vCode:'123456'},login:{vCode:'222222'},findPwd:{vCode:'333333'}]
         */
        if("register".equals(type)){

        }else{
            check(userService.checkPhoneExist(verCodeVo.getPhone())> 0,"手机号不存在");
            if("login".equals(type)){

            }else if("findPwd".equals(type)){

            }else{
                return ResultUtils.error("type类型非法");
            }
        }

        VerCode verCode = new VerCode();
        BeanUtils.copyProperties(verCodeVo,verCode);

        verCode.setCode(vCode);
        verCode.setCreateTime(new Date());
        verCodeService.save(verCode);

        log.info("发送{}类型验证码:{}",type,vCode);
        return ResultUtils.ok();
    }

    /**
     * 注册
     * @param userVo
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    @MyLog(title = "用户模块",action = "注册")
    @ApiOperation(value = "注册")
    public  Result register(@Valid UserVo userVo) throws Exception{

        checkNot(userService.checkUserExist(userVo.getUsername())> 0,"用户已经存在");


        /**
         *   TODO 还缺个密码前端加密后台解密的过程(防止用户名密码明文传输)
         */


        /**
         *  TODO 缺个redis中拿出vCode验证码验证是否正确的过程
         */
        check("123456".equals(userVo.getPhoneCode()),"手机验证码错误");



        User user = new User();
        BeanUtils.copyProperties(userVo,user);

        user.setPassword(MD5Utils.getStrrMD5(user.getPassword()));
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        userService.save(user);

        return ResultUtils.ok();
    }




}
