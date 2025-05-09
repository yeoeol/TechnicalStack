package com.example.fcmpush.v2.dto.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

	SELECT_SUCCESS(HttpStatus.OK, "푸시 메시지 전송 완료");

	private final HttpStatus status;
	private final String message;
}
