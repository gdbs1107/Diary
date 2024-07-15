package Diary.diary.service;

import Diary.diary.Domain.Dto.PayDto;
import Diary.diary.Domain.entity.member.Member;
import Diary.diary.Domain.entity.member.Pay;
import Diary.diary.repository.MemberRepository;
import Diary.diary.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PayService {
    private final PayRepository payRepository;
    private final MemberRepository memberRepository;

    public PayDto toDto(Pay pay) {
        return PayDto.builder()
                .id(pay.getId())
                .cardNumber(pay.getCardNumber())
                .build();
    }

    public Pay toEntity(PayDto payDto, Long memberId) {
        Pay pay = new Pay();
        pay.setId(payDto.getId());
        pay.setCardNumber(payDto.getCardNumber());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        pay.setMember(member);
        return pay;
    }

    // 결제 정보 생성
    public PayDto createPay(Long memberId, PayDto payDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Pay pay = toEntity(payDto, memberId);
        pay.setMember(member);
        return toDto(payRepository.save(pay));
    }

    // 회원의 모든 결제 정보 조회
    public List<PayDto> getAllPaysByMember(Long memberId) {
        return payRepository.findAllByMemberId(memberId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 결제 정보 수정
    public PayDto updatePay(Long payId, PayDto updatedPayDto) {
        Pay existingPay = payRepository.findById(payId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        existingPay.setCardNumber(updatedPayDto.getCardNumber());
        // 필요한 경우 다른 필드도 추가로 업데이트

        return toDto(payRepository.save(existingPay));
    }

    // 결제 정보 삭제
    public void deletePay(Long payId) {
        Pay existingPay = payRepository.findById(payId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        payRepository.delete(existingPay);
    }

    // 특정 결제 정보 조회
    public PayDto getPayById(Long payId) {
        Pay pay = payRepository.findById(payId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        return toDto(pay);
    }
}
