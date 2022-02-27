package com.sofa;

import com.sofa.utils.*;
import com.sofa.vo.MailVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class JavaNewsApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailUtils mailUtils;

	@Autowired
	private SmsUtils smsUtils;

	@Autowired
	private MapUtils mapUtils;

	@Test
	void contextLoads() {

		System.out.println(passwordEncoder.encode(MD5Utils.md5("123456")));
	}

	@Test
	void testMail(){
		MailVo mailVo=new MailVo();
		mailVo.setReceivers(new String[]{"1106203432@qq.com"});
		mailVo.setSubject("hello");
		mailVo.setContent("test");
		System.out.println(mailUtils.sendMail(mailVo));
	}


	@Test
	void test(){


	}
}
