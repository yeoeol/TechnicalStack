package com.example.hierarchicaldatalab.comment;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	public void create(CommentRequestDto requestDto) {

	}
}
