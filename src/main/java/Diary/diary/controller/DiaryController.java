package Diary.diary.controller;

import Diary.diary.Domain.Dto.DiaryDto;
import Diary.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Diary.diary.Domain.entity.Diary;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    // 일기 작성
    @PostMapping("/{memberId}/{bookId}")
    public ResponseEntity<DiaryDto> createDiary(@PathVariable Long memberId, @PathVariable Long bookId, @RequestBody DiaryDto diaryDto) {
        DiaryDto createdDiary = diaryService.createDiary(memberId, bookId, diaryDto);
        return ResponseEntity.ok(createdDiary);
    }

    // 자신이 쓴 전체 일기 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<DiaryDto>> getAllDiariesByMember(@PathVariable Long memberId) {
        List<DiaryDto> diaries = diaryService.getAllDiariesByMember(memberId);
        return ResponseEntity.ok(diaries);
    }

    // 원하는 날짜의 일기 조회
    @GetMapping("/{memberId}/date")
    public ResponseEntity<DiaryDto> getDiaryByDate(@PathVariable Long memberId, @RequestParam("date") LocalDateTime date) {
        DiaryDto diary = diaryService.getDiaryByDate(memberId, date);
        return ResponseEntity.ok(diary);
    }

    // 원하는 날짜의 일기 수정
    @PutMapping("/{memberId}/{diaryId}")
    public ResponseEntity<DiaryDto> updateDiary(
            @PathVariable Long memberId,
            @PathVariable Long diaryId,
            @RequestParam("password") String password,
            @RequestBody DiaryDto updatedDiaryDto) {
        DiaryDto diary = diaryService.updateDiary(memberId, diaryId, password, updatedDiaryDto);
        return ResponseEntity.ok(diary);
    }

    // 일기 삭제
    @DeleteMapping("/{memberId}/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @PathVariable Long memberId,
            @PathVariable Long diaryId,
            @RequestParam("password") String password) {
        diaryService.deleteDiary(memberId, diaryId, password);
        return ResponseEntity.noContent().build();
    }
}