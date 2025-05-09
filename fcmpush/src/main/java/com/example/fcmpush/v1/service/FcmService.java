package com.example.fcmpush.v1.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.fcmpush.v2.dto.FcmSendDto;

// @Service
public interface FcmService {
	int sendMessageTo(FcmSendDto fcmSendDto) throws IOException;
}
