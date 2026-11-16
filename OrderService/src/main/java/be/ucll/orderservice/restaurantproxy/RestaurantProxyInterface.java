package be.ucll.orderservice.restaurantproxy;

import be.ucll.orderservice.dto.Restaurant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface RestaurantProxyInterface {



    public List<Restaurant> findAll() ;

    public Restaurant findById(@PathVariable Long id);

    public Restaurant create(@RequestBody Restaurant restaurant) ;
}

