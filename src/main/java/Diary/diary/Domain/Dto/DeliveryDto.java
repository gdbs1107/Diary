package Diary.diary.Domain.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DeliveryDto {
    private Long id;
    private String street;
    private String zipcode;
    private String number;

    // Default constructor
    public DeliveryDto() {
    }

    // Parameterized constructor
    @Builder
    public DeliveryDto(Long id, String street, String zipcode, String number) {
        this.id = id;
        this.street = street;
        this.zipcode = zipcode;
        this.number = number;
    }
} 