package com.example.fcmpush.v2.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fcmpush.v1.service.FcmService;
import com.example.fcmpush.v2.dto.FcmTokenRequest;
import com.example.fcmpush.v2.service.FcmPushService;
import com.example.fcmpush.v2.service.FcmTokenService;
import com.example.fcmpush.v2.dto.ApiResponseWrapper;
import com.example.fcmpush.v2.dto.FcmSendDto;
import com.example.fcmpush.v2.dto.enums.SuccessCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController {

	// private final FcmService fcmService;
	private final FcmPushService fcmService;
	private final FcmTokenService fcmTokenService;

	@PostMapping
	public ResponseEntity<Void> registerToken(@RequestBody FcmTokenRequest request) {
		fcmTokenService.saveToken(request.getUserId(), request.getToken());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/send")
	public ResponseEntity<ApiResponseWrapper<Object>> pushMessage(@RequestBody @Validated FcmSendDto fcmSendDto) throws
		IOException {
		log.info("[+] 푸시 메시지를 전송합니다.");
		// int result = fcmService.sendMessageTo(fcmSendDto);
		fcmService.sendNotification(fcmSendDto.getToken(), fcmSendDto.getTitle(), fcmSendDto.getBody());

		ApiResponseWrapper<Object> arw = ApiResponseWrapper
			.builder()
			.result(1)
			.resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
			.resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
		return new ResponseEntity<>(arw, HttpStatus.OK);
	}
}
