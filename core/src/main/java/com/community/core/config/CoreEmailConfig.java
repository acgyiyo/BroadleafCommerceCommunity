package com.community.core.config;

import java.util.Properties;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.message.MessageCreator;
import org.broadleafcommerce.common.email.service.message.NullMessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Shared email configuration
 *
 * @author Phillip Verheyden (phillipuniverse)
 */
@Configuration
public class CoreEmailConfig {

  /**
   * A dummy mail sender has been set to send emails for testing purposes only
   * To view the emails sent use "DevNull SMTP" (download separately) with the
   * following setting: Port: 30000
   *
   * @return JavaMailSender object
   */
  @Bean
  public JavaMailSender blMailSender() {
    JavaMailSenderImpl sender = new JavaMailSenderImpl();

    Properties javaMailProps = new Properties();
    javaMailProps.setProperty("mail.smtp.host", "smtp.gmail.com");
    javaMailProps.setProperty("mail.smtp.starttls.enable", "true");
    javaMailProps.setProperty("mail.smtp.auth", "true");
    javaMailProps.setProperty("mail.smtp.timeout", "25000");
    javaMailProps.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
    javaMailProps.setProperty("mail.smtp.port", "587");

//    Session session = Session.getDefaultInstance(javaMailProps, new javax.mail.Authenticator() {
//      protected PasswordAuthentication getPasswordAuthentication() {
//        return new PasswordAuthentication("acgyiyo1@gmail.com", "Aeado250/");
//      }
//    });

//    sender.setSession(session);

    sender.setUsername("acgyiyo1@gmail.com");
    sender.setPassword("Aeado250/");
    sender.setJavaMailProperties(javaMailProps);

    try {
      sender.testConnection();
    } catch (MessagingException e) {
      System.out.println("provlemas aca");
      System.out.println(e);
    }
    return sender;
  }
//
//  @Bean
//  @Autowired
//  public MessageCreator blMessageCreator(
//      @Qualifier("blEmailTemplateEngine") TemplateEngine tlTemplateEngine, @Qualifier("blMailSender") MailSender mailSender) {
//    return new TemplateEmailMessage(tlTemplateEngine, mailSender);
//  }

//  @Bean
//  @Autowired
//  public MessageCreator blMessageCreator(@Qualifier("blMailSender") JavaMailSender mailSender) {
//    System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
//    System.out.println(mailSender);
//    return new NullMessageCreator(mailSender);
//  }

  @Bean
  @Autowired
  public MessageCreator blMessageCreator(@Qualifier("blMailSender") JavaMailSender mailSender) {
    System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    System.out.println(mailSender);
    return new EmailMessageCreator(mailSender);
  }

  @Bean
  public EmailInfo blEmailInfo() {
    EmailInfo info = new EmailInfo();
    info.setFromAddress("support@mycompany.com");
    info.setSendAsyncPriority("2");
    info.setSendEmailReliableAsync("false");
    return info;
  }

  @Bean
  public EmailInfo blRegistrationEmailInfo() {
    EmailInfo info = blEmailInfo();
    info.setSubject("You have successfully registered!");
    info.setEmailTemplate("register-email");
    return info;
  }

  @Bean
  public EmailInfo blForgotPasswordEmailInfo() {
    EmailInfo info = blEmailInfo();
    info.setSubject("Reset password request");
    info.setEmailTemplate("resetPassword-email");
    return info;
  }

  @Bean
  public EmailInfo blOrderConfirmationEmailInfo() {
    EmailInfo info = blEmailInfo();
    info.setSubject("Your order with The Heat Clinic");
    info.setEmailTemplate("orderConfirmation-email");
    return info;
  }

  @Bean
  public EmailInfo blFulfillmentOrderTrackingEmailInfo() {
    EmailInfo info = blEmailInfo();
    info.setSubject("Your order with The Heat Clinic Has Shipped");
    info.setEmailTemplate("fulfillmentOrderTracking-email");
    return info;
  }

  @Bean
  public EmailInfo blReturnAuthorizationEmailInfo() {
    EmailInfo info = blEmailInfo();
    info.setSubject("Your return with The Heat Clinic");
    info.setEmailTemplate("returnAuthorization-email");
    return info;
  }

  @Bean
  public EmailInfo blReturnConfirmationEmailInfo() {
    EmailInfo info = blEmailInfo();
    info.setSubject("Your return with The Heat Clinic");
    info.setEmailTemplate("returnConfirmation-email");
    return info;
  }
}
