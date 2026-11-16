package be.ucll.orderhistoryservice.api;

import be.ucll.orderhistoryservice.business.OrderHistoryService;
import be.ucll.orderhistoryservice.persistence.OrderHistory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderhistory")
public class OrderHistoryController {

    public final OrderHistoryService orderHistoryService;

    public OrderHistoryController(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping
    public List<OrderHistory> findAll() {
        return orderHistoryService.findAll();
    }

    @GetMapping("/query")
    public List<OrderHistory> findById(@RequestParam Long id) {
        return orderHistoryService.findByCustomerId(id);
    }


}
