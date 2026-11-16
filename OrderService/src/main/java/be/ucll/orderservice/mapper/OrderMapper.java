package be.ucll.orderservice.mapper;

import be.ucll.orderservice.dto.Customer;
import be.ucll.orderservice.dto.LimitedMenuItemDTO;
import be.ucll.orderservice.dto.OrderDTO;
import be.ucll.orderservice.dto.Restaurant;
import be.ucll.orderservice.persistence.Order;

import java.util.List;

public class OrderMapper {

    public static OrderDTO toDTO(Order order, Customer customer, List<LimitedMenuItemDTO> lmiList, Restaurant restaurant){
        return new OrderDTO(order.getId(), customer,order.getStatus(),order.getTotal(),lmiList,restaurant);
    }
}
