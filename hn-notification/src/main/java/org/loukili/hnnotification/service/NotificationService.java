package org.loukili.hnnotification.service;

import org.loukili.hnnotification.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();

    Notification saveNotification(Notification notification);

    List<Notification> getAllNotificationsByUserUsername(String userUsername);
}
