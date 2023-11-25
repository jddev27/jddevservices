package com.jddev.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor

public class NotificationService {

    private final NotificationRepository notificationRepository;
    public void SendNotification(NotificationRequest notificationRequest){
        Notification notification = Notification.builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .message(notificationRequest.message())
                .sender("jddev")
                .sentAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

    }
}
