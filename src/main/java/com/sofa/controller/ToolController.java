package com.sofa.controller;

import com.sofa.service.SysUserService;
import com.sofa.utils.*;
import com.sofa.vo.MailVo;
import com.sofa.vo.RoadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/tool")
@Api(tags = "工具类")
public class ToolController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private SysUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MapUtils mapUtils;

    @ApiOperation(value = "七牛云文件上传")
    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file) throws IOException {
        String url = qiniuUtils.upload(file.getInputStream(), file.getOriginalFilename());
        return Result.success("文件上传成功",url);

    }

    @ApiOperation(value = "忘记密码邮件找回")
    @PostMapping("/forget/password")
    public Result forget(@RequestBody MailVo mail){
        mail.setSubject("sofa平台");
        Random random = new Random();
        int password = 100000 + random.nextInt(1000000);
        userService.updatePwdByMail(mail.getReceivers()[0],passwordEncoder.encode(MD5Utils.md5(String.valueOf(password))));
        mail.setContent("您的新密码："+ password +",请妥善保管");
        return Result.success(mailUtils.sendMail(mail));
    }

    @ApiOperation(value = "发送验证码")
    @PostMapping("/sms")
    public Result sendSms(String phoneNumber){
        Random random=new Random();
        int code=100000+ random.nextInt(899999);
        smsUtils.sendSms(phoneNumber,code);
        redisUtil.setValueTime(phoneNumber+"sms",code,5);
        return Result.success("验证码发送成功");
    }

    @ApiOperation(value = "路径规划")
    @PostMapping("/map")
    public Result findRoad(@RequestBody RoadVo roadVo){
        return Result.success("查询成功",mapUtils.findRoad(roadVo));

    }


}
