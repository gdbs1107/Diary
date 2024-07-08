package Diary.diary.Domain.entity.order;

import Diary.diary.Domain.entity.Diary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


//Book 수정하자
@Entity
@Getter@Setter
public class Book {

    @Column(name = "book_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookName;

    private int price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Diary> contents = new ArrayList<>();

    @OneToOne(mappedBy = "book",fetch = FetchType.LAZY)
    private Order order;
}
