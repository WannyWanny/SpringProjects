package com.hanjin.jpa.controller;

import com.hanjin.jpa.domain.BookEntity;
import com.hanjin.jpa.dto.BookResponse;
import com.hanjin.jpa.dto.CreateBookRequest;
import com.hanjin.jpa.dto.SearchBookRequest;
import com.hanjin.jpa.dto.UpdateBookRequest;
import com.hanjin.jpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<BookResponse>> listBooks(){
        List<BookEntity> bookList = bookRepository.findAll();
        List<BookResponse> list = bookList.stream().map(
                book -> getBookResponse(book)).collect(Collectors.toList()
        );

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable int bookId){

        BookEntity book = bookRepository.findById(bookId).orElse(new BookEntity());

        BookResponse response = getBookResponse(book);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private BookResponse getBookResponse(BookEntity book) {
        BookResponse response = new BookResponse();
        response.setBookId(book.getBookId());
        response.setTitle(book.getTitle());
        response.setSubTitle(book.getSubTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setFullPrice(book.getFullPrice());
        return response;
    }

    @PostMapping("/books:search")
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestBody SearchBookRequest request){
        List<BookEntity> bookList = bookRepository.findAllByTitleContaining(request.getTitle());
        List<BookResponse> list = bookList.stream().map(
          book -> getBookResponse(book)).collect(Collectors.toList()
        );
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookEntity> createBook(@RequestBody CreateBookRequest request){
        BookEntity book = new BookEntity();
        book.setAuthor(request.getAuthor());
        book.setTitle(request.getTitle());
        book.setSubTitle(request.getSubTitle());
        book.setIsbn(request.getIsbn());
        book.setFullPrice(request.getFullPrice());
        book = bookRepository.save(book);

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponse> updateBook(@RequestBody UpdateBookRequest request, @PathVariable int bookId){
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new NoResultException());

        book.setBookId(bookId);
        book.setTitle(request.getTitle());
        book.setSubTitle(request.getSubTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setFullPrice(request.getFullPrice());

        book = bookRepository.save(book);
        return new ResponseEntity<>(getBookResponse(book), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<BookResponse> deleteBook(@PathVariable int bookId){
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new NoResultException());

        bookRepository.delete(book);
        return new ResponseEntity<>(getBookResponse(book), HttpStatus.OK);
    }
}
