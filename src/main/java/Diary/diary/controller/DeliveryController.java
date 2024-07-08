package Diary.diary.controller;

import Diary.diary.Domain.Dto.DeliveryDto;
import Diary.diary.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 배송 정보 생성
    @PostMapping
    public ResponseEntity<DeliveryDto> createDelivery(@RequestParam Long memberId, @RequestBody DeliveryDto deliveryDto) {
        DeliveryDto createdDelivery = deliveryService.createDelivery(memberId, deliveryDto);
        return ResponseEntity.ok(createdDelivery);
    }

    // 특정 회원의 모든 배송 정보 조회
    @GetMapping
    public ResponseEntity<List<DeliveryDto>> getAllDeliveriesByMember(@RequestParam Long memberId) {
        List<DeliveryDto> deliveries = deliveryService.getAllDeliveriesByMember(memberId);
        return ResponseEntity.ok(deliveries);
    }

    // 특정 배송 정보 조회
    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryDto> getDeliveryById(@PathVariable Long deliveryId) {
        DeliveryDto delivery = deliveryService.getDeliveryById(deliveryId);
        return ResponseEntity.ok(delivery);
    }

    // 배송 정보 수정
    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryDto> updateDelivery(
            @PathVariable Long deliveryId,
            @RequestBody DeliveryDto updatedDeliveryDto) {
        DeliveryDto delivery = deliveryService.updateDelivery(deliveryId, updatedDeliveryDto);
        return ResponseEntity.ok(delivery);
    }

    // 배송 정보 삭제
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }
}
