package com.community.core.config;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.message.MessageCreator;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

public class TemplateEmailMessage extends MessageCreator {

    private final TemplateEngine templateEngine;

    public TemplateEmailMessage(TemplateEngine templateEngine, MailSender mailSender) {
        super((JavaMailSender) mailSender);
        this.templateEngine = templateEngine;
    }

    @Override
    public String buildMessageBody(EmailInfo info, Map<String, Object> props) {
        final Context context = new Context();
        BroadleafRequestContext broadContext = BroadleafRequestContext.getBroadleafRequestContext();

        if (broadContext != null && broadContext.getJavaLocale() != null) {
            context.setLocale(broadContext.getJavaLocale());
        }

        if (props != null) {
            props.keySet().forEach(key -> context.setVariable(key, props.get(key)));
        }

        return this.templateEngine.process(info.getEmailTemplate(), context);
    }
}
