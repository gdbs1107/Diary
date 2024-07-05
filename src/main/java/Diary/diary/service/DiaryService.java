package Diary.diary.service;

import Diary.diary.Domain.entity.Diary;
import Diary.diary.Domain.entity.member.Member;
import Diary.diary.repository.DiaryRepository;
import Diary.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    // 일기 작성
    public Diary createDiary(Long memberId, Diary diary) {
        validate(diary.getContent().length() <= 255, "일기는 255자까지 작성 할 수 있습니다.");
        validate(!diary.getDiaryDate().isAfter(LocalDateTime.now()), "날짜를 다시 선택해주세요.");

        Member member = getMemberById(memberId);
        diary.setMember(member);
        return diaryRepository.save(diary);
    }

    // 자신이 쓴 전체 일기 조회
    public List<Diary> getAllDiariesByMember(Long memberId) {
        return diaryRepository.findAllByMemberId(memberId);
    }

    // 원하는 날짜의 일기 조회
    public Diary getDiaryByDate(Long memberId, LocalDateTime date) {
        return diaryRepository.findByMemberIdAndDiaryDate(memberId, date)
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜에 작성된 일기를 찾을 수 없습니다."));
    }

    // 원하는 날짜의 일기 수정 (비밀번호 검토)
    public Diary updateDiary(Long memberId, Long diaryId, String password, Diary updatedDiary) {
        Member member = getMemberById(memberId);
        validate(member.getPassword().equals(password), "비밀번호가 일치하지 않습니다.");

        Diary existingDiary = getDiaryById(diaryId);
        validate(existingDiary.getMember().getId().equals(memberId), "Diary does not belong to the member.");

        existingDiary.setTitle(updatedDiary.getTitle());
        existingDiary.setContent(updatedDiary.getContent());
        existingDiary.setDiaryDate(updatedDiary.getDiaryDate());
        existingDiary.setWeather(updatedDiary.getWeather());
        // 필요한 경우 다른 필드도 추가로 업데이트

        return diaryRepository.save(existingDiary);
    }

    // 일기 삭제
    public void deleteDiary(Long memberId, Long diaryId, String password) {
        Member member = getMemberById(memberId);
        validate(member.getPassword().equals(password), "비밀번호가 일치하지 않습니다.");

        Diary existingDiary = getDiaryById(diaryId);
        validate(existingDiary.getMember().getId().equals(memberId), "Diary does not belong to the member.");

        diaryRepository.delete(existingDiary);
    }




    // 회원 조회 메서드
    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
    }

    // 일기 조회 메서드
    private Diary getDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));
    }

    // 일반 검증 메서드
    private static void validate(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
