package com.vinayak.project.uber.uberApp;

import com.vinayak.project.uber.uberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;




	@Test
	void contextLoads() {
		emailSenderService.sendEmail("wetenay900@merotx.com","Test Email","Hello There , this is a test Email");
	}

}
