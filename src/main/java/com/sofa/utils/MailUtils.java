package com.sofa.utils;

import com.sofa.vo.MailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Component
@Slf4j
public class MailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public String sendMail(MailVo mailVo){
        try {
            if(mailVo.isHtml()){
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setTo(mailVo.getReceivers());
                mimeMessageHelper.setSubject(mailVo.getSubject());
                mimeMessageHelper.setText(mailVo.getContent(),true);
                mailSender.send(mimeMessage);
                log.info("HTML邮箱发送成功！收件人-->{}", Arrays.asList(mailVo.getReceivers()));
            }else{
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(from);
                mailMessage.setTo(mailVo.getReceivers());
                mailMessage.setSubject(mailVo.getSubject());
                mailMessage.setText(mailVo.getContent());
                mailSender.send(mailMessage);
                log.info("HTML邮箱发送成功！收件人-->{}", Arrays.asList(mailVo.getReceivers()));
            }
            return "邮件发送成功";
        }catch (Exception e){
            log.error("邮件发送失败-->{}",e.getMessage());
            return "邮件发送失败";
        }
    }
}
