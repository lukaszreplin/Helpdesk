package eu.replin.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService{

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Async
    public void sendSimpleMessage(String to, String subject, String template, Context context) {
//        Context context = new Context();
//        context.setVariable("header", "System zgłoszeń HelpDesk");
//        context.setVariable("title", "Zmiana statusu zgłoszenia");
        MimeMessage simpleMailMessage = javaMailSender.createMimeMessage();
        try {
            simpleMailMessage.setRecipients(Message.RecipientType.TO, to);
            simpleMailMessage.setFrom("helpdesk@replin.eu");
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setContent(templateEngine.process(template, context), "text/html; charset=utf-8");
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }


        javaMailSender.send(simpleMailMessage);
    }


}
