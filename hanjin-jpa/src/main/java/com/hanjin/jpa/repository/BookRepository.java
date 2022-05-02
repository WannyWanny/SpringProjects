package com.hanjin.jpa.repository;

import com.hanjin.jpa.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    List<BookEntity> findAllByTitleContaining(String title);
}
