package Diary.diary.service;

import Diary.diary.Domain.entity.member.Delivery;
import Diary.diary.Domain.entity.member.Member;
import Diary.diary.Domain.entity.member.Pay;
import Diary.diary.Domain.entity.order.Book;
import Diary.diary.Domain.entity.order.Order;
import Diary.diary.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final PayRepository payRepository;
    private final DeliveryRepository deliveryRepository;
    private final BookRepository bookRepository;

    // CRUD operations
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
    }

    public Order updateOrder(Long orderId, Order updatedOrder) {
        Order existingOrder = getOrderById(orderId);

        existingOrder.setBook(updatedOrder.getBook());
        existingOrder.setDelivery(updatedOrder.getDelivery());
        existingOrder.setPay(updatedOrder.getPay());

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long orderId) {
        Order existingOrder = getOrderById(orderId);
        orderRepository.delete(existingOrder);
    }

    // 주문 로직
    public String placeOrder(Long memberId, Long bookId, Long payId, Long deliveryId, int amount) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
        Pay pay = payRepository.findById(payId)
                .orElseThrow(() -> new IllegalArgumentException("Payment method not found with ID: " + payId));
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery address not found with ID: " + deliveryId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        // 제품 가격보다 결제수단의 돈이 많은지 검증
        if (pay.getCardNumber() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        // 돈을 빼서 내 계좌에 입금 (가상 계좌 번호 "1234")
        int remainingAmount = pay.getCardNumber() - amount;
        pay.setCardNumber(remainingAmount);

        // 주문 생성 및 저장
        Order order = new Order();
        order.setBook(book);
        order.setMember(member);
        order.setPay(pay);
        order.setDelivery(delivery);

        orderRepository.save(order);

        return "Payment completed. Remaining balance: " + remainingAmount;
    }

    // 자신이 가진 결제수단 조회
    public List<Pay> getPaymentMethods(Long memberId) {
        return payRepository.findAllByMemberId(memberId);
    }
}
