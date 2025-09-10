package com.ontime.office.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithImage(String toEmail, String subject, String htmlBody, String imagePath) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("amalaithayan@gmail.com"); // your sender
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody + "<br><img src='cid:image001'/>", true);
            helper.addInline("image001", new org.springframework.core.io.ClassPathResource(imagePath));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
