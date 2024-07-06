package Diary.diary.service;

import Diary.diary.Domain.entity.member.Member;
import Diary.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //회원정보 저장
    public Long save(Member member) {
        if (!nameDuplicateValid(member.getName())) {
            return memberRepository.save(member).getId();
        } else {
            throw new IllegalArgumentException("사용중인 이름입니다");
        }
    }

    //전체 회원 조회
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }


    // 한 명의 회원 정보 조회
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다"));
    }

    // 회원 정보 수정
    public Member updateMember(Long memberId, Member updatedMember) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다"));

        existingMember.setName(updatedMember.getName());
        existingMember.setBirthDate(updatedMember.getBirthDate());
        existingMember.setEmail(updatedMember.getEmail());
        // 필요한 경우 다른 필드도 추가로 업데이트

        return memberRepository.save(existingMember);
    }

    // 회원 정보 수정(비밀번호)
    public Member updateMemberPassword(Long memberId, Member updatedMember) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다"));

        existingMember.setPassword(updatedMember.getPassword());
        return memberRepository.save(existingMember);
    }


    // 이메일을 통해 비밀번호 조회
    public String getPasswordByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + email));
        return member.getPassword();
    }










    //이름 중복확인 메서드
    public boolean nameDuplicateValid(String name) {
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            if (member.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }



    // 회원이 존재하는지 검증하는 메서드
    public boolean isMemberExists(Long memberId) {
        return memberRepository.existsById(memberId);
    }

    //생일을 다 따로 받아야하나 아니면 한번에 받아서 유효검사를 해야하나
    public boolean isBirthDateValid(String birthDate) {
        try {
            LocalDate.parse(birthDate);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
