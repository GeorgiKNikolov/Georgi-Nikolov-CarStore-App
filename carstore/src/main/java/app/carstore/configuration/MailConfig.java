package app.carstore.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javamailSender (
            @Value("${mail.host}") String host,
            @Value("${mail.port}") Integer port,
            @Value("${mail.username}") String user,
            @Value("${mail.password}") String password
    ){

        JavaMailSenderImpl javamailSender = new JavaMailSenderImpl();
            javamailSender.setHost(host);
            javamailSender.setPort(port);
            javamailSender.setUsername(user);
            javamailSender.setPassword(password);
            javamailSender.setJavaMailProperties(mailProperties());
            javamailSender.setDefaultEncoding("UTF-8");
            return javamailSender;

    }


    private Properties mailProperties (){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.auth","true");


        return properties;

    }


}


