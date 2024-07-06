package Diary.diary.service;

import Diary.diary.Domain.entity.order.Book;
import Diary.diary.repository.BookRepository;
import Diary.diary.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final DiaryRepository diaryRepository;

    // 책 생성
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }


    // 책 조회
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
    }

    // 책 수정
    public Book updateBook(Long bookId, Book updatedBook) {
        Book existingBook = getBookById(bookId);

        existingBook.setBookName(updatedBook.getBookName());
        existingBook.setPrice(updatedBook.getPrice());
        // 필요한 경우 다른 필드도 추가로 업데이트

        return bookRepository.save(existingBook);
    }

    // 책 삭제
    public void deleteBook(Long bookId) {
        Book existingBook = getBookById(bookId);
        bookRepository.delete(existingBook);
    }

    /*// 일기를 모아서 책으로 묶는 메서드
    public Book createBookFromDiaries(Long memberId, List<Long> diaryIds, String bookName, int price) {
        List<Diary> diaries = diaryRepository.findAllById(diaryIds);
        if (diaries.isEmpty()) {
            throw new IllegalArgumentException("일기를 찾을 수 없습니다.");
        }

        Book book = new Book();
        book.setBookName(bookName);
        book.setPrice(price);
        book.setContents(diaries);

        // 책을 저장
        Book savedBook = bookRepository.save(book);

        // 일기를 책에 추가
        for (Diary diary : diaries) {
            diary.setBook(savedBook);
            diaryRepository.save(diary);
        }

        return savedBook;
    }*/
}