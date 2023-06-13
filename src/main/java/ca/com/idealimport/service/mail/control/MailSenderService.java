package ca.com.idealimport.service.mail.control;


import ca.com.idealimport.service.mail.entity.dto.MailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class MailSenderService {
    private final String mailFrom;


    private final JavaMailSender emailSender;


    private final SpringTemplateEngine templateEngine;

    private final MailTemplateService mailTemplateService;


    public MailSenderService(@Value("${spring.mail.username}") String mailFrom, JavaMailSender emailSender, SpringTemplateEngine templateEngine, MailTemplateService mailTemplateService) {
        this.mailFrom = mailFrom;
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.mailTemplateService = mailTemplateService;
    }

    public void sendEmail(MailDTO mailDTO, String mailTemplateName) {
        try {
            var mailTemplate = mailTemplateService.getMailTemplateByName(mailTemplateName);
            var message = emailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            var context = new Context();
            context.setVariables(mailDTO.requiredData());
            var html = templateEngine.process(mailTemplate.getFileName(), context);
            helper.setTo(mailDTO.mailTo());
            helper.setText(html, true);
            helper.setSubject(mailTemplate.getSubject());
            helper.setFrom(mailFrom);
            emailSender.send(message);
        } catch (MessagingException e) {
            log.error("MailSenderService.sendEmail error ={}", e.getMessage());
        }
    }
}
