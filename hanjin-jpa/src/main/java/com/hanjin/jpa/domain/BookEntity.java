package com.hanjin.jpa.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private int bookId;

    @Column(nullable = false)
    private String title;       //책 제목

    @Column(nullable = true)
    private String subTitle;    //책 부제목

    @Column(nullable = false, length = 13)
    private String isbn;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, name="full_price")
    private float fullPrice;    //가격

    @Column(name="is_active", columnDefinition = "tinyint default 0")
    private boolean isActive;   //판매여부
}
