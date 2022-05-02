package com.hanjin.jpa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateBookRequest {

    private String title;       //책 제목

    @JsonProperty("sub_title")
    private String subTitle;    //책 부제목
    private String isbn;
    private String author;

    @JsonProperty("full_price")
    private float fullPrice;    //가격
}
