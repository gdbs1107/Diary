package Diary.diary.Domain.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayDto {
    private Long id;
    private int cardNumber;

    // Default constructor
    public PayDto() {}

    // Parameterized constructor
    public PayDto(Long id, int cardNumber) {
        this.id = id;
        this.cardNumber = cardNumber;
    }
}
