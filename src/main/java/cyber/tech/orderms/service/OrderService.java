package cyber.tech.orderms.service;

import cyber.tech.orderms.controller.dto.OrderResponse;
import cyber.tech.orderms.entity.Order;
import cyber.tech.orderms.entity.OrderItem;
import cyber.tech.orderms.listener.dto.OrderEvent;
import cyber.tech.orderms.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;

  public void saveOrder(OrderEvent orderEvent) {
    var order =
        Order.builder()
            .id(orderEvent.codigoPedido())
            .customerId(orderEvent.codigoCliente())
            .total(getTotalOrder(orderEvent))
            .items(getOrderItems(orderEvent))
            .build();

    orderRepository.save(order);
  }

  public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
    return orderRepository
        .findAllByCustomerId(customerId, pageRequest)
        .map(OrderResponse::fromEntity);
  }

  private static BigDecimal getTotalOrder(OrderEvent orderEvent) {
    return orderEvent.itens().stream()
        .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private static List<OrderItem> getOrderItems(OrderEvent orderEvent) {
    return orderEvent.itens().stream()
        .map(
            i ->
                OrderItem.builder()
                    .product(i.produto())
                    .quantity(i.quantidade())
                    .price(i.preco())
                    .build())
        .toList();
  }
}
