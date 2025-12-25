package cyber.tech.orderms.listener.dto;

import java.util.List;

public record OrderEvent(Long codigoPedido, Long codigoCliente, List<OrderItemEvent> itens) {}
