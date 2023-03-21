package com.lark.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    /**
     * 使用构造器注入，该字段一定要加final修饰
     */
    final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    String username;

    @RequestMapping("/send")
    public Object send() {
        boolean status = sendMail("", "test", "this is a mail message!");
        System.out.println("status = " + status);
        return "success";
    }

    private boolean sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setBcc();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }

}
