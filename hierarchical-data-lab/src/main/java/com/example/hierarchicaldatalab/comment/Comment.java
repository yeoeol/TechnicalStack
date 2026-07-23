package com.example.hierarchicaldatalab.comment;

import com.example.hierarchicaldatalab.global.BaseEntity;
import jakarta.persistence.*;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@Column(name = "parent_id", nullable = true)
	private Long parentId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "deleted_at", nullable = true)
	private Instant deletedAt;

	public Comment(Long parentId, Long userId, String content) {
		this.parentId = parentId;
		this.userId = userId;
		this.content = content;
	}

	public static Comment create(Long parentId, Long userId, String content) {
		return new Comment(parentId, userId, content);
	}

	public boolean isDeleted() {
		return deletedAt != null;
	}

	public void delete() {
		deletedAt = Instant.now();
	}
}
