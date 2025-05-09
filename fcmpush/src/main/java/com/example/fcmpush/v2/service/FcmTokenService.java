package com.example.fcmpush.v2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.fcmpush.v2.entity.FcmToken;
import com.example.fcmpush.v2.repository.FcmTokenRepository;

@Service
public class FcmTokenService {

	private final FcmTokenRepository fcmTokenRepository;

	public FcmTokenService(FcmTokenRepository fcmTokenRepository) {
		this.fcmTokenRepository = fcmTokenRepository;
	}

	public void saveToken(Long userId, String token) {
		FcmToken fcmToken = new FcmToken(userId, token);
		fcmTokenRepository.save(fcmToken);
	}

	public Optional<String> getTokenByUserId(Long userId) {
		return fcmTokenRepository.findByUserId(userId).map(FcmToken::getToken);
	}

	public List<FcmToken> getUsers() {
		return fcmTokenRepository.findAll();
	}
}
