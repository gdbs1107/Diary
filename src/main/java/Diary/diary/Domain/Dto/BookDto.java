package Diary.diary.Domain.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BookDto {
    private Long id;
    private String bookName;
    private int price;

    // Default constructor
    public BookDto() {}

    @Builder
    public BookDto(Long id, String bookName, int price) {
        this.id = id;
        this.bookName = bookName;
        this.price = price;
    }

}
