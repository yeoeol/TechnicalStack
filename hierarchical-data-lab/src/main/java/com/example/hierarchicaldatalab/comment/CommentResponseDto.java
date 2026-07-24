package com.example.hierarchicaldatalab.comment;

public record CommentResponseDto(
		Long commentId,
		Long parentId,
		Long userId,
		String content,
		boolean deleted
) {
	public static CommentResponseDto from(Comment comment) {
		return new CommentResponseDto(
				comment.getId(),
				comment.getParentId(),
				comment.getUserId(),
				comment.isDeleted()
					? "삭제된 댓글입니다."
					: comment.getContent(),
				comment.isDeleted()
		);
	}
}
