package com.xiaoma.job.util;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;

@Slf4j
public class SMSUtils {

    private SMSUtils(){}

    // 短信应用 SDK AppID
    private final static int appid = 1400247169;

    // 短信应用 SDK AppKey
   private final static String appkey = "8696ea8b825b5d7128fccf98c727dc2f";


    public static Boolean sendCodeByPhone(String phone,String content){

        //"【小马兼职网】您的验证码是: " + code
        try {
            SmsSingleSender sender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = sender.send(0,"86",phone,content,"","");
            log.info("发送短信返回结果:{}",result);
            return true;
        } catch (HTTPException e) {
            log.error("发送短信响应码错误");
            e.printStackTrace();
        }catch (JSONException e) {
            log.error("发送短信JSON解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("发送短信网络IO错误");
            e.printStackTrace();
        }
        return false;
    }



}
