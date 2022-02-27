package com.sofa.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class SmsUtils {
    @Value("${aliyun.accessKey}")
    private String accessKey;

    @Value("${aliyun.secretKey}")
    private String secretKey;

    @Value("${aliyun.signName}")
    private String signName;

    @Value("${aliyun.templateCode}")
    private String templateCode;

    public Client createClient() throws Exception{
        Config config = new Config()
                .setAccessKeyId(accessKey)
                .setAccessKeySecret(secretKey);
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    public boolean sendSms(String phoneNumbers,int code){
        try {

            Client client = this.createClient();
            SendSmsRequest request=new SendSmsRequest()
                    .setPhoneNumbers(phoneNumbers)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam("{code: "+code+"}");
            SendSmsResponse response=client.sendSms(request);
            log.info("短信发送结果-->{}",response.getBody().code+"----"+response.getBody().getMessage());
            return true;
        } catch (Exception e) {
            log.error("短信发送失败-->{}",e.getMessage());
            return false;
        }
    }

}
