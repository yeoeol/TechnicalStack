package com.example.fcmpush.v1.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.fcmpush.v2.dto.FcmSendDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class FcmServiceImpl implements FcmService {

	/**
	 * 푸시 메시지 처리를 수행하는 비즈니스 로직
	 *
	 * @param fcmSendDto 모바일에서 전달받은 Object
	 * @return 성공(1), 실패(0)
	 */
	@Override
	public int sendMessageTo(FcmSendDto fcmSendDto) throws IOException {
		String message = makeMessage(fcmSendDto);
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters()
			.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + getAccessToken());

		HttpEntity entity = new HttpEntity<>(message, headers);

		String API_URL = "https://fcm.googleapis.com/v1/projects/test-31ff7/messages:send";
		ResponseEntity response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

		System.out.println(response.getStatusCode());

		return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
	}

	/**
	 * Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급 받습니다.
	 */
	private String getAccessToken() throws IOException {
		String firebaseConfigPath = "firebase/serviceAccountKey.json";

		GoogleCredentials googleCredentials = GoogleCredentials
			.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
			.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}

	/**
	 * FCM 전송 정보를 기반으로 메시지를 구성합니다. (Object -> String)
	 *
	 * @param fcmSendDto FcmSendDto
	 * @return String
	 */
	private String makeMessage(FcmSendDto fcmSendDto) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
			.message(FcmMessageDto.Message.builder()
				.token(fcmSendDto.getToken())
				.notification(FcmMessageDto.Notification.builder()
					.title(fcmSendDto.getTitle())
					.body(fcmSendDto.getBody())
					.image(null)
					.build()
				).build()).validateOnly(false).build();

		return om.writeValueAsString(fcmMessageDto);
	}

}
