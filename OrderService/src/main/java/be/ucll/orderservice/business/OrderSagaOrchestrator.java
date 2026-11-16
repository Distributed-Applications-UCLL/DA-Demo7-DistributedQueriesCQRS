package be.ucll.orderservice.business;

import be.ucll.orderservice.customerproxy.api.CustomerControllerApi;
import be.ucll.orderservice.dto.*;
import be.ucll.orderservice.mapper.OrderMapper;
import be.ucll.orderservice.menuitemproxy.MenuItemProxyInterface;
import be.ucll.orderservice.messaging.OrderEventPublisher;
import be.ucll.orderservice.messaging.dto.*;
import be.ucll.orderservice.persistence.Order;
import be.ucll.orderservice.persistence.OrderItem;
import be.ucll.orderservice.persistence.OrderRepository;
import be.ucll.orderservice.persistence.OrderStatus;
import be.ucll.orderservice.restaurantproxy.RestaurantProxyInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderSagaOrchestrator {

        private final OrderRepository orderRepository;
        private final OrderEventPublisher publisher;

    private final CustomerControllerApi customerControllerApi;
    private final MenuItemProxyInterface menuItemProxy;
    private final RestaurantProxyInterface restaurantProxy;


        public OrderSagaOrchestrator(
                OrderRepository orderRepository,
                OrderEventPublisher publisher, CustomerControllerApi customerControllerApi, MenuItemProxyInterface menuItemProxy, RestaurantProxyInterface restaurantProxy) {

            this.orderRepository = orderRepository;
            this.publisher = publisher;
            this.customerControllerApi = customerControllerApi;
            this.menuItemProxy = menuItemProxy;
            this.restaurantProxy = restaurantProxy;
        }

        public void startSaga(Order order) {

            // The first event ID is also the Saga ID.
            String eventId = UUID.randomUUID().toString();

            OrderCreatedEvent event = new OrderCreatedEvent(
                    eventId,
                    eventId,
                    order.getId(),
                    order.getCustomer(),
                    order.getTotal()
            );

            publisher.publishOrderCreated(event);
        }

        public void handlePaymentSucceeded(PaymentSucceededEvent event) {

            CreateDeliveryCommand command = new CreateDeliveryCommand(
                    UUID.randomUUID().toString(),
                    event.sagaId(),
                    event.orderId()
            );

            publisher.publishCreateDelivery(command);
        }

        @Transactional
        public void handlePaymentFailed(PaymentFailedEvent event) {

            Order order = findOrder(event.orderId());

            order.setStatus(OrderStatus.CANCELLED);

            orderRepository.save(order);
        }

        @Transactional
        public void handleDeliveryCreated(DeliveryCreatedEvent event) {

            Order order = findOrder(event.orderId());

            order.setStatus(OrderStatus.CONFIRMED);

            orderRepository.save(order);

            Customer customer = customerControllerApi.findById(order.getCustomer());
            Restaurant restaurant=restaurantProxy.findById(order.getRestaurant());
            List<LimitedMenuItemDTO> lmiDTOs = new ArrayList<>();

            for (OrderItem item: order.getItems()){
                MenuItemDTO mi= menuItemProxy.findById(item.getMenuItem());
                lmiDTOs.add(new LimitedMenuItemDTO(mi.id(),mi.name(),mi.price(), item.getQuantity()));
            }

            OrderDTO orderDTO =OrderMapper.toDTO(order,customer,lmiDTOs,restaurant);
            OrderCompletedEvent oce = new OrderCompletedEvent(
                    UUID.randomUUID().toString(),
                    orderDTO
            );

          publisher.publishOrderCompleted(oce);

        }

        public void handleDeliveryFailed(DeliveryFailedEvent event) {

            RefundPaymentCommand command = new RefundPaymentCommand(
                    UUID.randomUUID().toString(),
                    event.sagaId(),
                    event.orderId()
            );

            publisher.publishRefundPayment(command);
        }

        @Transactional
        public void handlePaymentRefunded(PaymentRefundedEvent event) {

            Order order = findOrder(event.orderId());

            order.setStatus(OrderStatus.CANCELLED);

            orderRepository.save(order);
        }

        private Order findOrder(Long orderId) {

            return orderRepository.findById(orderId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Order not found: " + orderId
                            )
                    );
        }
}
