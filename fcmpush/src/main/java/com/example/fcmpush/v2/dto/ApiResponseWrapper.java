package com.example.fcmpush.v2.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponseWrapper<T> {
	private int result;
	private HttpStatus resultCode;
	private String resultMsg;


}
