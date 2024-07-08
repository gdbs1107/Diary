package Diary.diary.controller;

import Diary.diary.Domain.Dto.OrderDto;
import Diary.diary.Domain.entity.member.Pay;
import Diary.diary.Domain.entity.order.Order;
import Diary.diary.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 모든 주문 조회
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // 특정 주문 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }

    // 주문 수정
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto updatedOrderDto) {
        OrderDto order = orderService.updateOrder(orderId, updatedOrderDto);
        return ResponseEntity.ok(order);
    }

    // 주문 삭제
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // 결제 수단 조회
    @GetMapping("/payments/{memberId}")
    public ResponseEntity<List<Pay>> getPaymentMethods(@PathVariable Long memberId) {
        List<Pay> payments = orderService.getPaymentMethods(memberId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByMember(@PathVariable Long memberId) {
        List<OrderDto> orders = orderService.getAllOrdersByMember(memberId);
        return ResponseEntity.ok(orders);
    }
}