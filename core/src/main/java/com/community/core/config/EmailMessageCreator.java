package com.community.core.config;

import org.broadleafcommerce.common.email.domain.EmailTarget;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.message.EmailPropertyType;
import org.broadleafcommerce.common.email.service.message.MessageCreator;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

public class EmailMessageCreator extends MessageCreator {

  private TemplateEngine templateEngine;

  public EmailMessageCreator(TemplateEngine templateEngine, MailSender mailSender) {
    this(mailSender);
    this.templateEngine = templateEngine;
  }

  public EmailMessageCreator(MailSender mailSender) {
    super((JavaMailSender) mailSender);
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

  @Override
  public void sendMessage(final Map<String, Object> props) throws MailException {
    SimpleMailMessage message = new SimpleMailMessage();

    EmailInfo info = (EmailInfo) props.get(EmailPropertyType.INFO.getType());
    EmailTarget emailUser = (EmailTarget) props.get(EmailPropertyType.USER.getType());

    message.setSubject(info.getSubject());
    message.setText(info.getMessageBody());
    message.setTo(emailUser.getEmailAddress());
    message.setFrom(info.getFromAddress());

    this.getMailSender().send(message);
  }
}
