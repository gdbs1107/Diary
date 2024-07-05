package Diary.diary.repository;

import Diary.diary.Domain.entity.member.Pay;
import Diary.diary.Domain.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayRepository extends JpaRepository<Pay,Long> {
    List<Pay> findAllByMemberId(Long memberId);
}
