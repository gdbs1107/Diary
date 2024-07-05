package Diary.diary.controller;

import Diary.diary.Domain.entity.member.Member;
import Diary.diary.service.MemberService;
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
    public ResponseEntity<Long> createMember(@RequestBody Member member) {
        try {
            Long memberId = memberService.save(member);
            return ResponseEntity.ok(memberId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 전체 회원 조회
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // 단일 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        try {
            Member member = memberService.getMemberById(id);
            return ResponseEntity.ok(member);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {
        try {
            Member member = memberService.updateMember(id, updatedMember);
            return ResponseEntity.ok(member);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원 비밀번호 수정ㅎㅎㅎ
    @PutMapping("/{id}/password")
    public ResponseEntity<Member> updateMemberPassword(@PathVariable Long id, @RequestBody Member updatedMember) {
        try {
            Member member = memberService.updateMemberPassword(id, updatedMember);
            return ResponseEntity.ok(member);
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
