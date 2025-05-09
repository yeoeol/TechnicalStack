package com.example.fcmpush.v2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * POST https://fcm.googleapis.com/v1/projects/test-31ff7/messages:send HTTP/1.1
 *
 * Content-Type: application/json
 * Authorization: Bearer ya29.ElqKBGN2Ri_Uz...HnS_uNreA
 *
 * {
 *    "message":{
 *       "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
 *       "notification":{
 *         "body":"This is an FCM notification message!",
 *         "title":"FCM Message"
 *       }
 *    }
 * }
 */

@Getter
@Builder
public class FcmMessageDto {

	private boolean validateOnly;
	private FcmMessageDto.Message message;

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Message {
		private FcmMessageDto.Notification notification;
		private String token;
	}

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Notification {
		private String title;
		private String body;
		private String image;
	}
}
