package com.application.mrmason.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.application.mrmason.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailsender;
	
	@Override
	public void sendEmail(String toMail, String otp){
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setTo(toMail);
		mail.setSubject("YOUR OTP FOR VERIFICATION.");
		String body="Thanks for registering with us. Your OTP to verify your email is "+otp+" - www.mrmason.in";
		mail.setText(body);
		mailsender.send(mail);
	}
	public void sendWebMail(String toMail, String body) {

		MimeMessage message = mailsender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
        	helper.setTo(toMail);
    		helper.setSubject("OTP LOGIN SUCCESSFUL");
    		helper.setText(body,true);
    		mailsender.send(message);
        }catch (MessagingException e) {
            // Handle exception
        }
	}

}
