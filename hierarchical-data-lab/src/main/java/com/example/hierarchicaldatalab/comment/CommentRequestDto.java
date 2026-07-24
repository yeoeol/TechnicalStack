package com.example.hierarchicaldatalab.comment;

public record CommentRequestDto(
		Long parentId,
		Long userId,
		String content
) {
}
