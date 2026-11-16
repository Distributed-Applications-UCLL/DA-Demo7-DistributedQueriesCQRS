package be.ucll.menuitemservice.mapper;

import be.ucll.menuitemservice.dto.MenuItemDTO;
import be.ucll.menuitemservice.dto.Restaurant;
import be.ucll.menuitemservice.persistence.MenuItem;

public class MenuItemMapper {

    public static MenuItemDTO toDTO(MenuItem menuItem, Restaurant restaurant){
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getPrice(),
                restaurant
        );
    }
}
