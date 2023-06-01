package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.SimpleOrderDto;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * X to One 성능 최적화
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {

        // stream 돌면서 쿼리가 member, delivery 각각 order 개수만큼 나감. = N + 1문제 발생
        return orderRepository.findAllByString(new OrderSearch())
            .stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    }

    // 재사용성 높지만, 불필요한 필드들도 조회하기 때문에 v4보다는 성능이 떨어짐
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        return orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    }

    // 필요한 필드만 가져오기 때문에 v3보다 성능은 좋지만, 다른 api에서 재사용하기 어려움
    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }
}
