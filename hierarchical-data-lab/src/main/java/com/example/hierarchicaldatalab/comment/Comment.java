package com.example.hierarchicaldatalab.comment;

import com.example.hierarchicaldatalab.global.BaseEntity;
import jakarta.persistence.*;

import lombok.Getter;

@Entity
@Getter
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
}
