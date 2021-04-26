package com.neusoft.ehrss.liaoning.security.password.mobile;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.utils.DemoDesUtil;

import java.io.UnsupportedEncodingException;

public class Demo {

    public static void main(String[] args) throws Exception {
        String encryptUser="n5qB6tQO9Up24XnuZkvkMK0+smtvtINBDGzrOGIiVvTGDUsHjzmhQl6ShwuXANwhK4JE1xMri5WU\n" +
                "ynp9Hqb12ajS2zoD1jMZ14H1VNsgyhMJ2AdUOm0vUg==";
      JSONObject  json = JSONObject.parseObject(DemoDesUtil.decrypt(encryptUser, DemoDesUtil.getDtKey()));
        System.out.println("success");
    }
}
