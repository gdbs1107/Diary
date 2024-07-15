package Diary.diary.controller;

import Diary.diary.Domain.Dto.MemberDto;
import Diary.diary.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 정보 저장
    @PostMapping
    public ResponseEntity<Long> createMember(@RequestBody @Valid MemberDto memberDto) {
        try {
            Long memberId = memberService.save(memberDto);
            return ResponseEntity.ok(memberId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 전체 회원 조회
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        List<MemberDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // 단일 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long id) {
        try {
            MemberDto memberDto = memberService.getMemberById(id);
            return ResponseEntity.ok(memberDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable Long id, @RequestBody MemberDto updatedMemberDto) {
        try {
            MemberDto memberDto = memberService.updateMember(id, updatedMemberDto);
            return ResponseEntity.ok(memberDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원 비밀번호 수정
    @PutMapping("/{id}/password")
    public ResponseEntity<MemberDto> updateMemberPassword(@PathVariable Long id, @RequestBody String newPassword) {
        try {
            MemberDto memberDto = memberService.updateMemberPassword(id, newPassword);
            return ResponseEntity.ok(memberDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 이메일을 통해 비밀번호 조회
    @GetMapping("/password")
    public ResponseEntity<String> getPasswordByEmail(@RequestParam String email) {
        try {
            String password = memberService.getPasswordByEmail(email);
            return ResponseEntity.ok(password);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
