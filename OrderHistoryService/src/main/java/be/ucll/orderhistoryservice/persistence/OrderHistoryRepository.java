package be.ucll.orderhistoryservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByCustomerId(Long customerId);
}
