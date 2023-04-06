package org.loukili.hnnotification.service;


import lombok.RequiredArgsConstructor;
import org.loukili.hnnotification.entity.Notification;
import org.loukili.hnnotification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
