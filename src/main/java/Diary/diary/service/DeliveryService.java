package Diary.diary.service;

import Diary.diary.Domain.Dto.DeliveryDto;
import Diary.diary.Domain.entity.member.Delivery;
import Diary.diary.Domain.entity.member.Member;
import Diary.diary.repository.DeliveryRepository;
import Diary.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;

    public DeliveryDto toDto(Delivery delivery) {
        return new DeliveryDto(
                delivery.getId(),
                delivery.getStreet(),
                delivery.getZipcode(),
                delivery.getNumber()
        );
    }

    public Delivery toEntity(DeliveryDto deliveryDto, Long memberId) {
        Delivery delivery = new Delivery();
        delivery.setId(deliveryDto.getId());
        delivery.setStreet(deliveryDto.getStreet());
        delivery.setZipcode(deliveryDto.getZipcode());
        delivery.setNumber(deliveryDto.getNumber());
        delivery.setMember(memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다.")));
        return delivery;
    }

    // 배송 정보 생성
    public DeliveryDto createDelivery(Long memberId, DeliveryDto deliveryDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Delivery delivery = toEntity(deliveryDto, memberId);
        delivery.setMember(member);
        return toDto(deliveryRepository.save(delivery));
    }

    // 회원의 모든 배송 정보 조회
    public List<DeliveryDto> getAllDeliveriesByMember(Long memberId) {
        return deliveryRepository.findAllByMemberId(memberId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 배송 정보 수정
    public DeliveryDto updateDelivery(Long deliveryId, DeliveryDto updatedDeliveryDto) {
        Delivery existingDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("배송 정보를 찾을 수 없습니다."));

        existingDelivery.setStreet(updatedDeliveryDto.getStreet());
        existingDelivery.setZipcode(updatedDeliveryDto.getZipcode());
        existingDelivery.setNumber(updatedDeliveryDto.getNumber());

        return toDto(deliveryRepository.save(existingDelivery));
    }

    // 배송 정보 삭제
    public void deleteDelivery(Long deliveryId) {
        Delivery existingDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("배송 정보를 찾을 수 없습니다."));
        deliveryRepository.delete(existingDelivery);
    }

    public DeliveryDto getDeliveryById(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("배송 정보를 찾을 수 없습니다."));
        return toDto(delivery);
    }
}
