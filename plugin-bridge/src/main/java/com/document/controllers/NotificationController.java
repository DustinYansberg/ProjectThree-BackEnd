package com.document.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.document.models.Document;
import com.document.models.Notification;
import com.document.services.DocumentService;
import com.document.services.NotificationService;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
	this.notificationService = notificationService;
    }
    
    @GetMapping("test")
    public String getNotificationsById() {
	return "Hello" ;
    }


    @GetMapping("notifications/{identityId}")
    public ResponseEntity<String> getNotificationsById(@PathVariable String identityId) {
	System.out.println("Get Notifications By Id From API Gateway");
	return notificationService.getNotificationsByIdentity(identityId);
    }

    @PostMapping("create")
    public ResponseEntity<String> createNotification(@RequestBody Notification notification) {
	return notificationService.createNotification(notification);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<Integer> readAllNotificationsById(@PathVariable String identityId) {
	return notificationService.readAllNotificationsById(identityId);
    }
}
