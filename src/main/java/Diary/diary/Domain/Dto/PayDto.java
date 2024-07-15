package Diary.diary.Domain.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PayDto {
    private Long id;
    private int cardNumber;

    // Default constructor
    public PayDto() {}

    // Parameterized constructor
    @Builder
    public PayDto(Long id, int cardNumber) {
        this.id = id;
        this.cardNumber = cardNumber;
    }
}
