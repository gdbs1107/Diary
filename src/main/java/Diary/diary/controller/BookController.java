package Diary.diary.controller;

import Diary.diary.Domain.Dto.BookDto;
import Diary.diary.Domain.entity.order.Book;
import Diary.diary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 책 생성
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto createdBook = bookService.createBook(bookDto);
        return ResponseEntity.ok(createdBook);
    }

    // 책 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        try {
            BookDto book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 책 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto updatedBookDto) {
        try {
            BookDto book = bookService.updateBook(id, updatedBookDto);
            return ResponseEntity.ok(book);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 책 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}