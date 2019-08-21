package com.xiaoma.job.controller;

import com.google.code.kaptcha.Producer;
import com.google.common.collect.ImmutableMap;
import com.xiaoma.job.Excption.CheckExcption;
import com.xiaoma.job.entity.VerCode;
import com.xiaoma.job.log.MyLog;
import com.xiaoma.job.pojo.CodeType;
import com.xiaoma.job.pojo.Message;
import com.xiaoma.job.pojo.RedisType;
import com.xiaoma.job.pojo.Result;
import com.xiaoma.job.entity.User;
import com.xiaoma.job.security.SecurityConstant;
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
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
@Slf4j
public class UserController{


    @Autowired
    private Producer captcha;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private VerCodeService verCodeService;


    @Autowired
    private CacheManager cacheManager;


    @GetMapping("/login")
    @MyLog(title = "用户模块",action = "登录")
    @ApiOperation(value = "登录")
    public Result login(@Valid LoginVo loginVo) throws Exception{

        validateCaptcha(loginVo.getCode(), loginVo.getCodeKey());

        check(userService.checkUserExist(loginVo.getUsername())> 0,Message.ERROR_USER_NOT_EXSIT);
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
        return dbUser == null ? ResultUtils.error(Message.ERROR_USERNAMEORPASSWORD)  : ResultUtils.ok(dbUser);
    }

    /**
     * 检查用户名是否已经被注册
     * @param username
     * @return
     */
    @GetMapping("/checkUser")
    @MyLog(title = "用户模块",action = "检查用户名是否已经被注册")
    @ApiOperation(value = "检查用户名是否已经被注册")
    public Result checkUser(@ApiParam(name = "username", value = "用户名") @RequestParam String username){
        return  userService.checkUserExist(username) > 0 ? ResultUtils.error(Message.ERROR_USER_EXSIT) : ResultUtils.ok();
    }


    @GetMapping("/captcha")
    @MyLog(title = "用户模块",action = "获取验证码")
    @ApiOperation(value = "获取验证码")
    public Result getCaptcha(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String capText = captcha.createText();
        String key = UUID.randomUUID().toString().replace("-","");
        Cache cache = cacheManager.getCache(SecurityConstant.VCODE_CACHE_NAME);
        cache.put(key,capText);
        log.info("key是{},验证码是{}",key,capText);
        BufferedImage bi = captcha.createImage(capText);
        ImageIO.write(bi, "jpg", baos);
        String imgBase64 = Base64.encodeBase64String(baos.toByteArray());
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        return ResultUtils.ok(ImmutableMap.of(key,"data:image/jpeg;base64,"+imgBase64));
    }


    /**
     * 验证验证码有效性
     * @param vCode
     * @param vCodeKey
     * @return
     * @throws CheckExcption
     */
    private void validateCaptcha(String vCode, String vCodeKey) throws CheckExcption {
        //验证码缓存
        Cache cache = cacheManager.getCache(SecurityConstant.VCODE_CACHE_NAME);
        Cache.ValueWrapper valueWrapper = cache.get(vCodeKey);
        check(valueWrapper != null,Message.ERROR_CAOTCHA_KEY_ERROR);

        String vCodeCache = (String) valueWrapper.get();
        //使用验证码后，不管成功还是失败，都删除；保证仅一次有效
        cache.evict(vCodeKey);

        if(StringUtils.isBlank(vCode)){
            throw new CheckExcption(Message.ERROR_CAOTCHA_NULL);
        }

        if(StringUtils.isBlank(vCodeCache)){
            throw new CheckExcption(Message.ERROR_CAOTCHA_TIMEOUT);
        }
        if(!vCode.equals(vCodeCache)){
            throw new CheckExcption(Message.ERROR_CAOTCHA_ERROR);
        }
    }

    /**
     * 发送手机验证码
     * @param verCodeVo
     * @return
     */
    @PostMapping("/sendPhoneCode")
    @MyLog(title = "用户模块",action = "发送手机验证码")
    @ApiOperation(value = "发送手机验证码")
    public Result sendPhoneCode(@Valid VerCodeVo verCodeVo) throws Exception{
        String type = verCodeVo.getType();

        check(StringUtils.isNotBlank(type),"type不能为空");

        String phoneCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码;

        String content = "您的验证码是: " + phoneCode;
        /**
         * TODO 还需审核签名才能使用短信服务
         */
/*
        Boolean flag = SMSUtils.sendCodeByPhone(verCodeVo.getPhone(),content);
        if(!flag){
            return ResultUtils.error("内部错误,发送短信失败");
        }
*/


        /**
         *  把手机验证码存储到redis中(设置时效性为5分钟) 18578649851:[register:{phoneCode:'123456'},login:{phoneCode:'222222'},findPwd:{phoneCode:'333333'}]
         */
        if(!CodeType.REGISTER.equals(type)){
            check(userService.checkPhoneExist(verCodeVo.getPhone())> 0,"手机号不存在");
            if(!CodeType.LOGIN.equals(type) && !CodeType.FINDPASSWORD.equals(type)){
                return ResultUtils.error("type类型非法");
            }
        }

        Map<String,Map<String,String>> map = new HashMap<>();
        Map<String,String> map2 = new HashMap<>();
        map2.put("phoneCode",phoneCode);
        map.put(type,map2);
        redisTemplate.opsForValue().set(RedisType.VERCODE + verCodeVo.getPhone(),map,5, TimeUnit.MINUTES);


        VerCode verCode = new VerCode();
        BeanUtils.copyProperties(verCodeVo,verCode);

        verCode.setCode(phoneCode);
        verCode.setCreateTime(new Date());
        verCodeService.save(verCode);

        log.info("{}发送{}类型验证码:{}",verCodeVo.getPhone(),type,phoneCode);
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


        validateCaptcha(userVo.getCode(), userVo.getCodeKey());

        checkNot(userService.checkUserExist(userVo.getUsername())> 0,Message.ERROR_USER_EXSIT);


        /**
         *   TODO 还缺个密码前端加密后台解密的过程(防止用户名密码明文传输)
         */

        //取出redis缓存中的验证码进行校验

        Map<String,Map<String,String>> map = (Map<String,Map<String,String>>) redisTemplate.opsForValue().get(RedisType.VERCODE + userVo.getPhone());
        String phoneCode = "";
        if(map == null || map.get(CodeType.REGISTER) == null){
            return ResultUtils.error(Message.ERROR_PHONE_CAOTCHA_TIMEOUT);
        }else{
            phoneCode = map.get(CodeType.REGISTER).get("phoneCode");
        }
        check(phoneCode.equals(userVo.getPhoneCode()),Message.ERROR_PHONE_CAOTCHA_ERROR);


        //新增用户到数据库

        User user = new User();
        BeanUtils.copyProperties(userVo,user);

        user.setPassword(MD5Utils.getStrrMD5(user.getPassword()));
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        userService.save(user);


        //删除redis缓存的验证码
        redisTemplate.delete(RedisType.VERCODE + userVo.getPhone());


        return ResultUtils.ok(user);
    }




}
