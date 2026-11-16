package be.ucll.orderservice.business;
import be.ucll.orderservice.customerproxy.api.CustomerControllerApi;
import be.ucll.orderservice.dto.*;
import be.ucll.orderservice.mapper.OrderMapper;
import be.ucll.orderservice.menuitemproxy.MenuItemProxyInterface;
import be.ucll.orderservice.messaging.EventPublisher;
import be.ucll.orderservice.messaging.dto.OrderCreatedEvent;
import be.ucll.orderservice.persistence.Order;
import be.ucll.orderservice.persistence.OrderItem;
import be.ucll.orderservice.persistence.OrderRepository;
import be.ucll.orderservice.persistence.OrderStatus;
import be.ucll.orderservice.restaurantproxy.RestaurantProxy;
import be.ucll.orderservice.restaurantproxy.RestaurantProxyInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final CustomerControllerApi customerControllerApi;
    private final MenuItemProxyInterface menuItemProxy;
    private final RestaurantProxyInterface restaurantProxy;
    private final OrderSagaOrchestrator orchestrator;

    public OrderService(OrderRepository orderRepository, CustomerControllerApi customerControllerApi, MenuItemProxyInterface menuItemProxy, RestaurantProxyInterface restaurantProxy, OrderSagaOrchestrator orchestrator) {
        this.orderRepository = orderRepository;
        this.customerControllerApi = customerControllerApi;
        this.menuItemProxy = menuItemProxy;
        this.restaurantProxy = restaurantProxy;
        this.orchestrator = orchestrator;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public OrderDTO findById(Long id) {
        Order foundOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
        Customer customer = customerControllerApi.findById(foundOrder.getCustomer());
        Restaurant restaurant=restaurantProxy.findById(foundOrder.getRestaurant());
        List<LimitedMenuItemDTO> lmiDTOs = new ArrayList<>();

        for (OrderItem item: foundOrder.getItems()){
           MenuItemDTO mi= menuItemProxy.findById(item.getMenuItem());
           lmiDTOs.add(new LimitedMenuItemDTO(mi.id(),mi.name(),mi.price(), item.getQuantity()));
        }

        return OrderMapper.toDTO(foundOrder,customer,lmiDTOs,restaurant);
    }

    public Order save(Order order) {

            //find customer, restaurant and menuItems by id to check for DB integrity !!!
            Customer customer = customerControllerApi.findById(order.getCustomer());
            Restaurant restaurant = restaurantProxy.findById(order.getRestaurant());;
            for (OrderItem item : order.getItems()) {
                MenuItemDTO menuItem = menuItemProxy
                        .findById(item.getMenuItem());
            }
            //if something not found, a 500 will be thrown (irl do better exception handling!)s

            //if all well, go on with creating order and saga, otherwise exception
            //find items to calculate total
            BigDecimal total = this.calculateTotalPriceOfOrder(order);
            //BigDecimal total = new BigDecimal("1234");

            order.setTotal(total);
            order.setStatus(OrderStatus.PENDING);

            //already save order in pending state
            order = orderRepository.save(order);

            // 5. Start the Saga.
            orchestrator.startSaga(order);

            return order;

    }


    public List<Customer> findAllCustomers(){
        return customerControllerApi.findAll();
    }

    private BigDecimal calculateTotalPriceOfOrder(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {

            MenuItemDTO menuItem = menuItemProxy
                    .findById(item.getMenuItem());

            BigDecimal itemTotal = menuItem.price()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(itemTotal);

        }
        return total;
    }

}
