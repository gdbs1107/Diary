package Diary.diary.Domain.entity.member;

import Diary.diary.Domain.entity.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Delivery {

    @Column(name = "delivery_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String zipcode;
    private String number; //나중에 주소 제대로 리팩터링

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;
}
