package org.loukili.hnnotification.controller;


import lombok.RequiredArgsConstructor;
import org.loukili.hnnotification.entity.Notification;
import org.loukili.hnnotification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hn-notification/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Notification> getAllNotifications(){
        return notificationService.getAllNotifications();
    }


    // TODO: save a notification
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Notification saveNotification(@RequestBody Notification notification){
        return notificationService.saveNotification(notification);
    }


    // TODO: get all notifications of a user
    @GetMapping("/users/{userUsername}")
    @ResponseStatus(HttpStatus.OK)
    public List<Notification> getAllNotificationsByUserUsername(@PathVariable String userUsername){
        return notificationService.getAllNotificationsByUserUsername(userUsername);
    }
    // TODO: get not read notifications of a user

    // TODO: get notifications of a user by page
    // TODO: get notifications of authenticated user (extract username from request)
}
