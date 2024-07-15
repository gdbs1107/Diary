package Diary.diary.Domain.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderDto {
    private Long id;
    private Long bookId;
    private Long memberId;
    private Long deliveryId;
    private Long payId;

    // Default constructor
    public OrderDto() {
    }

    // Parameterized constructor
    @Builder
    public OrderDto(Long id, Long bookId, Long memberId, Long deliveryId, Long payId) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.deliveryId = deliveryId;
        this.payId = payId;
    }
}
