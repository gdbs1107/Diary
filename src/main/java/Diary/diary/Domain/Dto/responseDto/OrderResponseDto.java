package Diary.diary.Domain.Dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private final Long id;
    private final BookDto book;
    private final MemberDto member;
    private final DeliveryDto delivery;
    private final PayDto pay;

    @Builder
    public OrderResponseDto(Long id, BookDto book, MemberDto member, DeliveryDto delivery, PayDto pay) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.delivery = delivery;
        this.pay = pay;
    }

    @Getter
    @Builder
    public static class BookDto {
        private final Long id;
        private final String bookName;
        private final int price;
    }

    @Getter
    @Builder
    public static class MemberDto {
        private final Long id;
        private final String name;
        private final String email;
    }

    @Getter
    @Builder
    public static class DeliveryDto {
        private final Long id;
        private final String street;
        private final String zipcode;
        private final String number;
    }

    @Getter
    @Builder
    public static class PayDto {
        private final Long id;
        private final int cardNumber;
    }
}
