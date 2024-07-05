package Diary.diary.repository;

import Diary.diary.Domain.entity.Diary;
import Diary.diary.Domain.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary,Long> {
    List<Diary> findAllByMemberId(Long memberId);

    Optional<Diary> findByMemberIdAndDiaryDate(Long memberId, LocalDateTime diaryDate);
}
