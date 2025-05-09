package com.example.fcmpush.v2.service;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmPushService {

	private final FirebaseMessaging firebaseMessaging;

	public void sendNotification(String targetToken, String title, String body) {
		Notification notification = Notification.builder()
			.setTitle(title)
			.setBody(body)
			.build();

		Message message = Message.builder()
			.setToken(targetToken)
			.setNotification(notification)
			.build();

		try {
			String response = firebaseMessaging.send(message);
			System.out.println("푸시 성공: " + response);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
	}
}
