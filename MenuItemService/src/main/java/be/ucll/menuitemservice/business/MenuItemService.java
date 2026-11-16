package be.ucll.menuitemservice.business;

import be.ucll.menuitemservice.dto.MenuItemDTO;
import be.ucll.menuitemservice.dto.Restaurant;
import be.ucll.menuitemservice.mapper.MenuItemMapper;
import be.ucll.menuitemservice.persistence.MenuItem;
import be.ucll.menuitemservice.persistence.MenuItemRepository;
import be.ucll.menuitemservice.restaurantproxy.RestaurantProxyInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantProxyInterface restaurantProxy;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantProxyInterface restaurantProxy) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantProxy = restaurantProxy;
    }

    public List<MenuItemDTO> findAll() {
        List<MenuItem> allItems = menuItemRepository.findAll();
        return allItems.stream().map(item->MenuItemMapper.toDTO(item,restaurantProxy.findById(item.getRestaurant()))).toList();
    }

    public List<MenuItem> findByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public MenuItemDTO findById(Long id) {
        MenuItem mi =menuItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu item not found: " + id));
        Restaurant restaurant=restaurantProxy.findById(mi.getRestaurant());
        return MenuItemMapper.toDTO(mi,restaurant);
    }

    public MenuItem save(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

}
