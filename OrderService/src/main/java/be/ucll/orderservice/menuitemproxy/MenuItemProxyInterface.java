package be.ucll.orderservice.menuitemproxy;

import be.ucll.orderservice.dto.MenuItem;
import be.ucll.orderservice.dto.MenuItemDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MenuItemProxyInterface {

    public List<MenuItemDTO> findAll() ;
    public MenuItemDTO findById(@PathVariable Long id) ;

}
