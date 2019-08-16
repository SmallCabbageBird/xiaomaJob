package com.xiaoma.job.config;

import com.alibaba.druid.filter.config.ConfigTools;
import com.xiaoma.job.util.MD5Utils;

public class DataSourceConfig {


    public static void main(String[] args) {
      /*  try {
            String password = "wangtianxiang";
            String[] arr = ConfigTools.genKeyPair(512);

            // System.out.println("privateKey:" + arr[0]);
            System.out.println("publicKey:" + arr[1]);
            System.out.println("password:" + ConfigTools.encrypt(arr[0], password));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       String str1 = MD5Utils.getSaltMD5("wangtianxiang");
       String str2 = MD5Utils.getSaltMD5("wangtianxiang");
       System.out.println(str1);
       System.out.println(str2);
       System.out.println(str1 == str2);
    }



}
