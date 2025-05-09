package com.example.fcmpush.v2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class FcmToken {

	@Id @GeneratedValue
	private Long id;
	private Long userId;
	private String token;

	public FcmToken(Long userId, String token) {
		this.userId = userId;
		this.token = token;
	}

	public FcmToken() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
