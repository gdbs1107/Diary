package Diary.diary.service;

import Diary.diary.Domain.entity.member.Delivery;
import Diary.diary.Domain.entity.member.Member;
import Diary.diary.repository.DeliveryRepository;
import Diary.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {


    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;

    // 배송 정보 생성
    public Delivery createDelivery(Long memberId, Delivery delivery) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        delivery.setMember(member);
        return deliveryRepository.save(delivery);
    }

    // 회원의 모든 배송 정보 조회
    public List<Delivery> getAllDeliveriesByMember(Long memberId) {
        return deliveryRepository.findAllByMemberId(memberId);
    }

    // 배송 정보 수정
    public Delivery updateDelivery(Long deliveryId, Delivery updatedDelivery) {
        Delivery existingDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("배송 정보를 찾을 수 없습니다."));

        existingDelivery.setStreet(updatedDelivery.getStreet());
        existingDelivery.setZipcode(updatedDelivery.getZipcode());
        existingDelivery.setNumber(updatedDelivery.getNumber());
        // 필요한 경우 다른 필드도 추가로 업데이트

        return deliveryRepository.save(existingDelivery);
    }

    // 배송 정보 삭제
    public void deleteDelivery(Long deliveryId) {
        Delivery existingDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("배송 정보를 찾을 수 없습니다."));
        deliveryRepository.delete(existingDelivery);
    }
}