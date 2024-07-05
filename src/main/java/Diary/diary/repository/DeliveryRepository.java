package Diary.diary.repository;

import Diary.diary.Domain.entity.member.Delivery;
import Diary.diary.Domain.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findAllByMemberId(Long memberId);
}
