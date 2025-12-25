package cyber.tech.orderms.listener;

import cyber.tech.orderms.listener.dto.OrderEvent;
import cyber.tech.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static cyber.tech.orderms.config.RabbitMqConfig.ORDER_QUEUE;

@Component
@RequiredArgsConstructor
public class OrderListener {

  private final OrderService orderService;

  @RabbitListener(queues = ORDER_QUEUE)
  public void listener(Message<OrderEvent> message) {
    orderService.saveOrder(message.getPayload());
  }
}
