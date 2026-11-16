package be.ucll.deliveryservice.business;

import be.ucll.deliveryservice.dto.Order;
import be.ucll.deliveryservice.exception.DeliveryRejectedException;
import be.ucll.deliveryservice.messaging.dto.OrderCreatedEvent;
import be.ucll.deliveryservice.orderserviceproxy.OrderServiceProxyInterface;
import be.ucll.deliveryservice.persistence.Delivery;
import be.ucll.deliveryservice.persistence.DeliveryRepository;
import be.ucll.deliveryservice.persistence.DeliveryStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderServiceProxyInterface orderServiceProxy;

    public DeliveryService(DeliveryRepository deliveryRepository, OrderServiceProxyInterface orderServiceProxy) {
        this.deliveryRepository = deliveryRepository;
        this.orderServiceProxy = orderServiceProxy;
    }

    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    public Delivery findById(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new RuntimeException("Delivery not found: " + id));
    }


    public Order findOrderById(Long id){
        return orderServiceProxy.findById(id);
    }

    @Transactional
    public Delivery createDelivery(String sagaId, Long orderId) throws DeliveryRejectedException {

        var existing = deliveryRepository.findByOrderId(orderId);

        // Prevent duplicate deliveries.
        if (existing.isPresent()) {

            Delivery delivery = existing.get();
            return delivery;
        }

        // Example business validation.
        if (orderId <= 0) {
            throw new DeliveryRejectedException(
                    "Delivery cannot be created for this order"
            );
        }

        Delivery delivery = new Delivery();

        delivery.setOrderId(orderId);
        delivery.setStatus(DeliveryStatus.CREATED);
        delivery.setAddress("Some street, 3000 Leuven");

        return deliveryRepository.save(delivery);
    }
}
