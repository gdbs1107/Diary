package Diary.diary.service;

import Diary.diary.Domain.Dto.DiaryDto;
import Diary.diary.Domain.entity.Diary;
import Diary.diary.Domain.entity.member.Member;
import Diary.diary.repository.BookRepository;
import Diary.diary.repository.DiaryRepository;
import Diary.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public DiaryDto toDto(Diary diary) {
        return DiaryDto.builder()
                .id(diary.getId())
                .diaryDate(diary.getDiaryDate())
                .content(diary.getContent())
                .weather(diary.getWeather())
                .title(diary.getTitle())
                .build();
    }

    public Diary toEntity(DiaryDto diaryDto) {
        Diary diary = new Diary();
        diary.setId(diaryDto.getId());
        diary.setDiaryDate(diaryDto.getDiaryDate());
        diary.setContent(diaryDto.getContent());
        diary.setWeather(diaryDto.getWeather());
        diary.setTitle(diaryDto.getTitle());
        return diary;
    }

    // 일기 작성
    public DiaryDto createDiary(Long memberId, Long bookId, DiaryDto diaryDto) {
        validate(diaryDto.getContent().length() <= 255, "일기는 255자까지 작성할 수 있습니다.");
        validate(!diaryDto.getDiaryDate().isAfter(LocalDateTime.now()), "날짜를 다시 선택해주세요.");

        Member member = getMemberById(memberId);
        Diary diary = toEntity(diaryDto);
        diary.setMember(member);
        diary.setBook(bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("책 정보를 찾을 수 없습니다.")));
        return toDto(diaryRepository.save(diary));
    }

    // 자신이 쓴 전체 일기 조회
    public List<DiaryDto> getAllDiariesByMember(Long memberId) {
        return diaryRepository.findAllByMemberId(memberId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 원하는 날짜의 일기 조회
    public DiaryDto getDiaryByDate(Long memberId, LocalDateTime date) {
        return diaryRepository.findByMemberIdAndDiaryDate(memberId, date)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜에 작성된 일기를 찾을 수 없습니다."));
    }

    // 원하는 날짜의 일기 수정 (비밀번호 검토)
    public DiaryDto updateDiary(Long memberId, Long diaryId, String password, DiaryDto updatedDiaryDto) {
        Member member = getMemberById(memberId);
        validate(member.getPassword().equals(password), "비밀번호가 일치하지 않습니다.");

        Diary existingDiary = getDiaryById(diaryId);
        validate(existingDiary.getMember().getId().equals(memberId), "일기가 회원에 속하지 않습니다.");

        existingDiary.setTitle(updatedDiaryDto.getTitle());
        existingDiary.setContent(updatedDiaryDto.getContent());
        existingDiary.setDiaryDate(updatedDiaryDto.getDiaryDate());
        existingDiary.setWeather(updatedDiaryDto.getWeather());

        return toDto(diaryRepository.save(existingDiary));
    }

    // 일기 삭제
    public void deleteDiary(Long memberId, Long diaryId, String password) {
        Member member = getMemberById(memberId);
        validate(member.getPassword().equals(password), "비밀번호가 일치하지 않습니다.");

        Diary existingDiary = getDiaryById(diaryId);
        validate(existingDiary.getMember().getId().equals(memberId), "일기가 회원에 속하지 않습니다.");

        diaryRepository.delete(existingDiary);
    }

    // 회원 조회 메서드
    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
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
