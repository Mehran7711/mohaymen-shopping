package com.mohaymen.norification.service.impl;

import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentSucceededEvent;
import com.mohaymen.norification.service.INotificationService;
import com.mohaymen.norification.web.ApplicationProperties;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService implements INotificationService {
    private final JavaMailSender emailSender;
    private final ApplicationProperties properties;

    @Override
    public void sendOrderCreatedNotification(OrderCreatedEvent event) {
        String message =
                """
                ===================================================
                Order Created Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been created successfully.

                Thanks,
                Mehran Team
                ===================================================
                """
                        .formatted(event.getCustomerId(), event.getOrderNumber());
        log.info("\n{}", message);
        sendEmail(event.getEmail(), "Order Created Notification", message);
    }

    public void sendOrderCancelledNotification(PaymentFailedEvent event) {
        String message =
                """
                ===================================================
                Order Cancelled Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been cancelled.
                Reason: %s

                Thanks,
                BookStore Team
                ===================================================
                """
                        .formatted(event.getCustomerName(), event.getOrderId(), event.getReason());
        log.info("\n{}", message);
        sendEmail(event.getEmail(), "Order Cancelled Notification", message);
    }

    @Override
    public void sendOrderSuccessfulNotification(PaymentSucceededEvent event) {
        String message =
                """
                ===================================================
                Order Created Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been created successfully.

                Thanks,
                Mehran Team
                ===================================================
                """
                        .formatted(event.getCustomerName(), event.getOrderId());
        log.info("\n{}", message);
        sendEmail(event.getEmail(), "Order Created Notification", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(properties.supportEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            emailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
