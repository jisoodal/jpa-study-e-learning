package jpabook.jpashop.api;

import java.util.List;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    /*
        Hibernate5Module 기본 설정 인해
        Lazy Loading으로 인해 Proxy인 객체들은
        강제 초기화하지 않는 이상 데이터를 안 뿌려준다.
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // 초기화
            order.getDelivery().getAddress(); // 초기화
            List<OrderItem> orderItems = order.getOrderItems(); // 초기화
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName()); // 초기화
        }

        return all;
    }
}
