package be.ucll.orderhistoryservice.business;

import be.ucll.orderhistoryservice.persistence.OrderHistory;
import be.ucll.orderhistoryservice.persistence.OrderHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    // only find methods!!!
    public List<OrderHistory> findAll() {
        return orderHistoryRepository.findAll();
    }

    public List<OrderHistory> findByCustomerId(Long customerId) {
        return orderHistoryRepository.findByCustomerId(customerId);
    }



}
