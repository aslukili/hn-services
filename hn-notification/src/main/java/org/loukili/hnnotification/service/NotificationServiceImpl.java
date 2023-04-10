package org.loukili.hnnotification.service;


import lombok.RequiredArgsConstructor;
import org.loukili.hnnotification.entity.Notification;
import org.loukili.hnnotification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotificationsByUserUsername(String userUsername) {
        return notificationRepository.findAllByUserUsername(userUsername);
    }

    @Override
    public Notification readNotification(Long notification) {
        Notification optionalNotification = notificationRepository.findById(notification).orElseThrow();
            optionalNotification.setIsRead(true);
            return notificationRepository.save(optionalNotification);
        }

    }

