package cyber.tech.orderms.controller;

import cyber.tech.orderms.controller.dto.ApiResponse;
import cyber.tech.orderms.controller.dto.OrderResponse;
import cyber.tech.orderms.controller.dto.PaginationResponse;
import cyber.tech.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<ApiResponse<OrderResponse>> findAll(
      @PathVariable Long customerId,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {

    var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, size));

    return ResponseEntity.ok(
        new ApiResponse<>(pageResponse.getContent(), PaginationResponse.fromPage(pageResponse)));
  }
}
