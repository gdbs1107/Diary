package Diary.diary.service;

import Diary.diary.Domain.entity.member.Member;
import Diary.diary.Domain.entity.member.Pay;
import Diary.diary.repository.MemberRepository;
import Diary.diary.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PayService {
    private final PayRepository payRepository;
    private final MemberRepository memberRepository;

    // 결제 정보 생성
    public Pay createPay(Long memberId, Pay pay) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        pay.setMember(member);
        return payRepository.save(pay);
    }

    // 회원의 모든 결제 정보 조회
    public List<Pay> getAllPaysByMember(Long memberId) {
        return payRepository.findAllByMemberId(memberId);
    }

    // 결제 정보 수정
    public Pay updatePay(Long payId, Pay updatedPay) {
        Pay existingPay = payRepository.findById(payId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        existingPay.setCardNumber(updatedPay.getCardNumber());
        // 필요한 경우 다른 필드도 추가로 업데이트

        return payRepository.save(existingPay);
    }

    // 결제 정보 삭제
    public void deletePay(Long payId) {
        Pay existingPay = payRepository.findById(payId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        payRepository.delete(existingPay);
    }
}
