package Diary.diary.service;

import Diary.diary.Domain.Dto.OrderDto;
import Diary.diary.Domain.Dto.responseDto.OrderResponseDto;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final PayRepository payRepository;
    private final DeliveryRepository deliveryRepository;
    private final BookRepository bookRepository;

    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .bookId(order.getBook().getId())
                .memberId(order.getMember().getId())
                .deliveryId(order.getDelivery().getId())
                .payId(order.getPay().getId())
                .build();
    }

    public OrderResponseDto toResponseDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .book(OrderResponseDto.BookDto.builder()
                        .id(order.getBook().getId())
                        .bookName(order.getBook().getBookName())
                        .price(order.getBook().getPrice())
                        .build())
                .member(OrderResponseDto.MemberDto.builder()
                        .id(order.getMember().getId())
                        .name(order.getMember().getUsername())
                        .email(order.getMember().getEmail())
                        .build())
                .delivery(OrderResponseDto.DeliveryDto.builder()
                        .id(order.getDelivery().getId())
                        .street(order.getDelivery().getStreet())
                        .zipcode(order.getDelivery().getZipcode())
                        .number(order.getDelivery().getNumber())
                        .build())
                .pay(OrderResponseDto.PayDto.builder()
                        .id(order.getPay().getId())
                        .cardNumber(order.getPay().getCardNumber())
                        .build())
                .build();
    }

    public Order toEntity(OrderDto orderDto) {
        Member member = memberRepository.findById(orderDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + orderDto.getMemberId()));
        Pay pay = payRepository.findById(orderDto.getPayId())
                .orElseThrow(() -> new IllegalArgumentException("Payment method not found with ID: " + orderDto.getPayId()));
        Delivery delivery = deliveryRepository.findById(orderDto.getDeliveryId())
                .orElseThrow(() -> new IllegalArgumentException("Delivery address not found with ID: " + orderDto.getDeliveryId()));
        Book book = bookRepository.findById(orderDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + orderDto.getBookId()));

        Order order = new Order();
        order.setId(orderDto.getId());
        order.setMember(member);
        order.setPay(pay);
        order.setDelivery(delivery);
        order.setBook(book);

        return order;
    }


    // CRUD operations
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = toEntity(orderDto);
        return toDto(orderRepository.save(order));
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        return toResponseDto(order);
    }

    public OrderDto updateOrder(Long orderId, OrderDto updatedOrderDto) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        existingOrder.setBook(bookRepository.findById(updatedOrderDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + updatedOrderDto.getBookId())));
        existingOrder.setDelivery(deliveryRepository.findById(updatedOrderDto.getDeliveryId())
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with ID: " + updatedOrderDto.getDeliveryId())));
        existingOrder.setPay(payRepository.findById(updatedOrderDto.getPayId())
                .orElseThrow(() -> new IllegalArgumentException("Payment method not found with ID: " + updatedOrderDto.getPayId())));

        return toDto(orderRepository.save(existingOrder));
    }

    public void deleteOrder(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        orderRepository.delete(existingOrder);
    }

    // 자신이 가진 결제수단 조회
    public List<Pay> getPaymentMethods(Long memberId) {
        return payRepository.findAllByMemberId(memberId);
    }

    // 특정 회원의 모든 주문 정보 조회
    public List<OrderResponseDto> getAllOrdersByMember(Long memberId) {
        return orderRepository.findAllByMemberId(memberId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
