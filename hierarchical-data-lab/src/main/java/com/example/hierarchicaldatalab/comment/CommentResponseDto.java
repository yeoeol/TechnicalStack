package com.example.hierarchicaldatalab.comment;

public record CommentResponseDto(
		Long commentId,
		Long parentId,
		Long userId,
		String content
) {
	public static CommentResponseDto from(Comment comment) {
		return new CommentResponseDto(
				comment.getId(),
				comment.getParentId(),
				comment.getUserId(),
				comment.getContent()
		);
	}
}
