package ait.shop.service.implService;

import ait.shop.model.entity.User;
import ait.shop.service.interfaces.ConfirmCodeService;
import ait.shop.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final Configuration mailConfig;
    private final ConfirmCodeService confirmCodeService;

    public EmailServiceImpl(JavaMailSender mailSender, Configuration mailConfig, ConfirmCodeService confirmCodeService) {
        this.mailSender = mailSender;
        this.mailConfig = mailConfig;
        this.confirmCodeService = confirmCodeService;

        mailConfig.setDefaultEncoding("UTF-8");
        mailConfig.setTemplateLoader(new ClassTemplateLoader(EmailServiceImpl.class, "/mail/"));
    }

    @Override
    public void sendConfirmationEmail(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        String text = generateConfirmationEmail(user);

        try {
            helper.setFrom("lorageo33@gmail.com");
            helper.setTo(user.getEmail());
            helper.setSubject("Registration");
            helper.setText(text, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateConfirmationEmail(User user) {
        try {
            Template template = mailConfig.getTemplate("confirm_mail.ftlh");
            String code = confirmCodeService.generateConfirmCode(user);

            Map<String, Object> data = new HashMap<>();
            data.put("name", user.getUsername());
            data.put("link", "http://localhost:8080/register/" + code);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
