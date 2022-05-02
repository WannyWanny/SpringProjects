package com.hanjin.jpa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateBookRequest {
    private String title;       //책 제목

    @JsonProperty(value = "sub_title")
    private String subTitle;    //책 부제목
    private String isbn;
    private String author;

    @JsonProperty(value="full_price")
    private float fullPrice;    //가격
}
