package Diary.diary.Domain.entity;

import Diary.diary.Domain.entity.member.Member;
import Diary.diary.Domain.entity.order.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Diary {

    @Column(name = "diary_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime diaryDate;

    private String content;

    @Enumerated(EnumType.STRING)
    private Weather weather;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
