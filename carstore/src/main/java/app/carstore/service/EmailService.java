package app.carstore.service;


import app.carstore.model.entity.UserEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;


    public EmailService(TemplateEngine templateEngine,
                        MessageSource messageSource,
                        JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }

    public void sendRegistrationEmail(String email,
                                      String username,
                                      Locale preferredLocale,
                                      UserEntity user) {


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("georgikireto4@gmail.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(getEmailSubject(preferredLocale));
            mimeMessageHelper.setText(generateMessageContent(preferredLocale, username, user),true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        }catch (MessagingException e){
           throw new RuntimeException(e);
        }

    }

    private String getEmailSubject(Locale locale) {
        return messageSource.getMessage(
                "registration_subject",
                new Object[0],
                locale);
    }



        private String generateMessageContent(Locale locale, String username ,UserEntity user){

        Context ctx = new Context();
        ctx.setLocale(locale);
        ctx.setVariable("username", username);
        ctx.setVariable("link","http://localhost:8080/verify?code=" + user.getVerificationCode());
        return templateEngine.process("email/registration", ctx);
        }

    public void sendNewPassword(UserEntity user, Locale resolveLocale) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
           MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
           mimeMessageHelper.setFrom("georgikireto4@gmail.com");
           mimeMessageHelper.setTo(user.getEmail());
           mimeMessageHelper.setSubject(getEmailSubject(resolveLocale));
           mimeMessageHelper.setText(generateMessageContentResetPassword(resolveLocale,user),true);
           javaMailSender.send(mimeMessageHelper.getMimeMessage());

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

    private String generateMessageContentResetPassword(Locale locale,UserEntity user) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("username",user.getLastName());
        context.setVariable("randomPassword",user.getPassword());
        context.setVariable("link","http://localhost:8080/change");

        return templateEngine.process("email/forgotten-password", context);

    }

}
