package com.example.readfile.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class BookDto {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Integer price;
    private Integer discountPrice;
    private String description;
    private String publishedDate;
    private String imageUrl;
}
