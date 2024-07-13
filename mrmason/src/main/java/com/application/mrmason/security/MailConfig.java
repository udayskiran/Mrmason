package com.application.mrmason.security;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.application.mrmason.entity.AdminMail;
import com.application.mrmason.repository.AdminMailRepo;

@Configuration
public class MailConfig {

    @Autowired
    private AdminMailRepo mailRepo;

    @Bean
    public JavaMailSender getJavaMailSender() {
    	AdminMail smtpConfig = mailRepo.findByEmailid("Mrmason.in@kosuriers.com");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpConfig.getMailHost());
        mailSender.setPort(Integer.parseInt(smtpConfig.getSmtpPort()));
        mailSender.setUsername(smtpConfig.getEmailid());
        mailSender.setPassword(smtpConfig.getPwd());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpConfig.getSmtpAuth());
        props.put("mail.smtp.starttls.enable", smtpConfig.getStarttlsEnable());

        return mailSender;
    }
}