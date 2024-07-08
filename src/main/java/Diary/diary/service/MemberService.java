package Diary.diary.service;

import Diary.diary.Domain.Dto.MemberDto;
import Diary.diary.Domain.entity.member.Member;
import Diary.diary.Domain.entity.order.Book;
import Diary.diary.repository.BookRepository;
import Diary.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getEmail(), member.getBirthDate(), member.getPassword());
    }

    public Member toEntity(MemberDto memberDto) {
        Member member = new Member();
        member.setId(memberDto.getId());
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setBirthDate(memberDto.getBirthDate());
        member.setPassword(memberDto.getPassword());
        return member;
    }

    // 회원정보 저장
    // 회원정보 저장
    public Long save(MemberDto memberDto) {
        Member member = toEntity(memberDto);
        if (!nameDuplicateValid(member.getName())) {
            member = memberRepository.save(member);
            assignBookToMember(member); // 책 할당 로직 추가
            return member.getId();
        } else {
            throw new IllegalArgumentException("사용중인 이름입니다");
        }
    }

    // 전체 회원 조회
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 한 명의 회원 정보 조회
    public MemberDto getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다"));
        return toDto(member);
    }

    // 회원 정보 수정
    public MemberDto updateMember(Long memberId, MemberDto updatedMemberDto) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다"));

        existingMember.setName(updatedMemberDto.getName());
        existingMember.setBirthDate(updatedMemberDto.getBirthDate());
        existingMember.setEmail(updatedMemberDto.getEmail());
        existingMember.setPassword(updatedMemberDto.getPassword());
        // 필요한 경우 다른 필드도 추가로 업데이트

        return toDto(memberRepository.save(existingMember));
    }

    // 회원 정보 수정(비밀번호)
    public MemberDto updateMemberPassword(Long memberId, String newPassword) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다"));

        existingMember.setPassword(newPassword);
        return toDto(memberRepository.save(existingMember));
    }

    // 이메일을 통해 비밀번호 조회
    public String getPasswordByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + email));
        return member.getPassword();
    }

    // 이름 중복확인 메서드
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

    // 생일을 다 따로 받아야하나 아니면 한번에 받아서 유효검사를 해야하나
    public boolean isBirthDateValid(String birthDate) {
        try {
            LocalDate.parse(birthDate);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // 책 할당 로직 추가
    private void assignBookToMember(Member member) {
        Book book = new Book();
        book.setBookName("Default Book Name");
        book.setPrice(0);
        bookRepository.save(book);
    }
}