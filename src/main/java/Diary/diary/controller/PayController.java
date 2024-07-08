package Diary.diary.controller;

import Diary.diary.Domain.Dto.PayDto;
import Diary.diary.Domain.entity.member.Pay;
import Diary.diary.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pays")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    // 회원의 모든 결제 정보 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PayDto>> getAllPaysByMember(@PathVariable Long memberId) {
        List<PayDto> pays = payService.getAllPaysByMember(memberId);
        return ResponseEntity.ok(pays);
    }

    // 결제 정보 생성
    @PostMapping("/member/{memberId}")
    public ResponseEntity<PayDto> createPay(@PathVariable Long memberId, @RequestBody PayDto payDto) {
        PayDto createdPay = payService.createPay(memberId, payDto);
        return ResponseEntity.ok(createdPay);
    }

    // 결제 정보 조회
    @GetMapping("/{payId}")
    public ResponseEntity<PayDto> getPayById(@PathVariable Long payId) {
        PayDto pay = payService.getPayById(payId);
        return ResponseEntity.ok(pay);
    }

    // 결제 정보 수정
    @PutMapping("/{payId}")
    public ResponseEntity<PayDto> updatePay(@PathVariable Long payId, @RequestBody PayDto updatedPayDto) {
        PayDto pay = payService.updatePay(payId, updatedPayDto);
        return ResponseEntity.ok(pay);
    }

    // 결제 정보 삭제
    @DeleteMapping("/{payId}")
    public ResponseEntity<Void> deletePay(@PathVariable Long payId) {
        payService.deletePay(payId);
        return ResponseEntity.noContent().build();
    }
}