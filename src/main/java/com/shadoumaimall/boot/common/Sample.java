package com.shadoumaimall.boot.common;

// This file is auto-generated, don't edit it. Thanks.

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;

public class Sample {

    public static void main(String[] args_) throws Exception {
        Config config = new Config()
                //这里修改为我们上面生成自己的AccessKey ID
                .setAccessKeyId("LTAI5tMrcFhMc5YEiSqiVgtm")


                //这里修改为我们上面生成自己的AccessKey Secret
                .setAccessKeySecret("DKwCenEazIXV5H2VtKGIqcXUvXbK2U");
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = new Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")//短信签名
                .setTemplateCode("SMS_154950909")//短信模板
                .setPhoneNumbers("17631521837")//这里填写接受短信的手机号码
                .setTemplateParam("{\"code\":\"1234\"}");//验证码
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
    }
}
