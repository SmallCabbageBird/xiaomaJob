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

import com.xiaoma.job.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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
    @ApiOperation(value = "登录接口", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "验证码",required = true,paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
    })
    public Result login(UserVo user) throws Exception{
        check(StringUtils.isNotBlank(user.getCode()),"验证码不能为空");
        check(StringUtils.isNotBlank(user.getUsername()),"用户名不能为空");
        check(StringUtils.isNotBlank(user.getPassword()),"密码不能为空");


        check(userService.checkUserExist(user.getUsername())> 0,"用户不存在");
        /**
         *  TODO 验证验证码是否正确,还需写获取验证码的接口
         */


        /**
         *   TODO 还缺个密码前端加密后台解密的过程(防止用户名密码明文传输)
         */
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
    @ApiImplicitParam(name = "username", value = "用户名",required = true,paramType = "query")
    public Result checkUser(@RequestParam String username){
        return  userService.checkUserExist(username) > 0 ? ResultUtils.error("用户已经存在") : ResultUtils.ok();
    }


    /**
     * 发送手机验证码
     * @param type register 注册发送验证码  login登录发送验证码 findPwd找回密码发送验证码
     * @param phone
     * @return
     */
    @PostMapping("/sendPhoneCode")
    @MyLog(title = "用户模块",action = "发送手机验证码")
    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "手机验证码类型:register注册,login登录,findPwd找回密码",paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true, paramType = "query"),
    })
    public Result sendPhoneCode(VerCode verCode) throws Exception{
        String type = verCode.getType();
        check(userService.checkPhoneExist(verCode.getPhone())> 0,"手机号不存在");
        check(StringUtils.isNotBlank(type),"type不能为空");

        String vCode = "123456";
        /**
         * TODO 获取手机验证码对接短信平台
         */
        /**
         * TODO 把手机验证码存储到redis中(设置时效性为5分钟) 18578649851:[register:{vCode:'123456'},login:{vCode:'222222'},findPwd:{vCode:'333333'}]
         */
        if("register".equals(type)){

        }else if("login".equals(type)){

        }else if("findPwd".equals(type)){

        }else{
            return ResultUtils.error("type类型非法");
        }

        verCode.setCode(vCode);
        verCode.setCreateTime(new Date());
        verCodeService.save(verCode);

        log.info("发送{}类型验证码:{}",type,vCode);
        return ResultUtils.ok();
    }

    /**
     * 注册
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    @MyLog(title = "用户模块",action = "注册")
    @ApiOperation(value = "注册", notes = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vCode", value = "手机验证码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户姓名", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query"),
            @ApiImplicitParam(name = "age", value = "年龄", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "生日", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址id,如11/22/33", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "地址,如广东省/广州市/天河区", paramType = "query"),
            @ApiImplicitParam(name = "fullAddress", value = "详细地址,如车陂南车陂小学", paramType = "query"),
            @ApiImplicitParam(name = "isVip", value = "是否vip 0否 1是", paramType = "query"),
            @ApiImplicitParam(name = "integral", value = "用户积分", paramType = "query"),
    })
    public Result register(UserVo user) throws Exception{

        check(StringUtils.isNotBlank(user.getUsername()),"用户名不能为空");
        checkNot(userService.checkUserExist(user.getUsername())> 0,"用户已经存在");

        check(StringUtils.isNotBlank(user.getPassword()),"密码不能为空");
        check(StringUtils.isNotBlank(user.getPhone()),"手机号不能为空");
        check(StringUtils.isNotBlank(user.getVCode()),"验证码不能为空");


        /**
         *   TODO 还缺个密码前端加密后台解密的过程(防止用户名密码明文传输)
         */


        /**
         *  TODO 缺个redis中拿出vCode验证码验证是否正确的过程
         */
        check("123456".equals(user.getVCode()),"验证码错误");





        user.setPassword(MD5Utils.getStrrMD5(user.getPassword()));
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        userService.save(user);

        return ResultUtils.ok();
    }




}
