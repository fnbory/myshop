package com.februy.shop.service.email;

import com.februy.shop.exception.email.EmailTemplateNotFoundException;
import com.februy.shop.properties.EmailSubjectProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 2:09
 */
@Service
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail")
@Async("emailExecutor")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EmailSubjectProperties subjectProperties;

    private  String username;

    @Override
    public void sendHTML(String email, String subject, Map<String, Object> params, List<String> filePaths) {
        Context context = new Context();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        String emailContent = templateEngine.process(subject , context);
        send(email, subjectProperties.getProperty(subject), emailContent, filePaths);
    }

    @Override
    public void send(String to, String subject, String content, List<String> filePaths) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //true表示需要创建一个multipart message
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            if (filePaths != null && filePaths.size() > 0) {
                File file;
                FileSystemResource fileSystemResource;
                for (String filePath : filePaths) {
                    file = new File(filePath);
                    if (!file.exists()) {
                        throw new EmailTemplateNotFoundException(filePath);
                    }
                    fileSystemResource = new FileSystemResource(file);
                    helper.addAttachment(filePath.substring(filePath.lastIndexOf(File.separator)), fileSystemResource);
                }
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
    }
}
