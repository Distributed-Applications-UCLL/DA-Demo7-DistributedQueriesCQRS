package be.ucll.orderhistoryservice.messaging;

import be.ucll.orderhistoryservice.dto.LimitedMenuItemDTO;
import be.ucll.orderhistoryservice.persistence.OrderHistory;
import be.ucll.orderhistoryservice.persistence.OrderHistoryItem;
import be.ucll.orderhistoryservice.persistence.OrderHistoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderHistoryListener {

    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryListener(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @RabbitListener(
            queues = OrderHistoryMessagingConfig.ORDER_COMPLETED_QUEUE)
    public void onOrderCompleted(OrderCompletedEvent event) {
        List<OrderHistoryItem> items = new ArrayList<>();
        for (LimitedMenuItemDTO menuItem : event.orderDTO().items()) {
            OrderHistoryItem item = new OrderHistoryItem();
            item.setMenu_item_name(menuItem.name());
            item.setQuantity(menuItem.quantity());
            items.add(item);
        }
        OrderHistory orderHistory = new OrderHistory(
             items,
                event.orderDTO().total(),
                event.orderDTO().restaurant().getName(),
                event.orderDTO().customer().getId(),
                event.orderDTO().customer().getName()
        );

        orderHistoryRepository.save(orderHistory);

    }


}
