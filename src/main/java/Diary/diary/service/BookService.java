package Diary.diary.service;

import Diary.diary.Domain.Dto.BookDto;
import Diary.diary.Domain.entity.order.Book;
import Diary.diary.repository.BookRepository;
import Diary.diary.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final DiaryRepository diaryRepository;

    public BookDto toDto(Book book) {
        List<Long> diaryIds = book.getContents().stream()
                .map(diary -> diary.getId())
                .collect(Collectors.toList());
        return new BookDto(book.getId(), book.getBookName(), book.getPrice());
    }

    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setBookName(bookDto.getBookName());
        book.setPrice(bookDto.getPrice());
        return book;
    }

    // 책 생성
    public BookDto createBook(BookDto bookDto) {
        Book book = toEntity(bookDto);
        return toDto(bookRepository.save(book));
    }

    // 책 조회
    public BookDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
        return toDto(book);
    }

    // 책 수정
    public BookDto updateBook(Long bookId, BookDto updatedBookDto) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));

        existingBook.setBookName(updatedBookDto.getBookName());
        existingBook.setPrice(updatedBookDto.getPrice());

        return toDto(bookRepository.save(existingBook));
    }

    // 책 삭제
    public void deleteBook(Long bookId) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
        bookRepository.delete(existingBook);
    }
}