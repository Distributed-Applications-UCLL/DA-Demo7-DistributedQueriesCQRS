package be.ucll.orderservice.menuitemproxy;

import be.ucll.orderservice.dto.MenuItem;
import be.ucll.orderservice.dto.MenuItemDTO;
import be.ucll.orderservice.dto.Restaurant;
import be.ucll.orderservice.exception.MenuServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class MenuItemProxy implements MenuItemProxyInterface{

    private final RestTemplate restTemplate;
    private final CacheManager cacheManager;
    private String baseUrl = "http://localhost:8083/menu-items";

    public MenuItemProxy(RestTemplate restTemplate, CacheManager cacheManager) {
        this.restTemplate = restTemplate;
        this.cacheManager = cacheManager;
    }

    @Cacheable(
            value = "menu-items",
            key = "'all'"
    )
    public List<MenuItemDTO> findAll() {
        MenuItemDTO[] menuItems = restTemplate.getForObject(baseUrl,MenuItemDTO[].class);
        List<MenuItemDTO> menuItemList = Arrays.asList(menuItems);
        return menuItemList;
    }

    @CircuitBreaker(name="menuItemByIdBreaker", fallbackMethod = "fallBackById")
    public MenuItemDTO findById(Long id) {
        return restTemplate.getForObject(baseUrl+"/"+id,MenuItemDTO.class);
    }


    private MenuItemDTO fallBackById(Long id, Throwable exception){
        //simple version
        return new MenuItemDTO(id," fallback pizza",new BigDecimal(16),new Restaurant(1L,"my restaurant","somewhere in Leuven"));

        //with cache from findAll
        /*System.out.println("via fallback get from cache");
        Cache cache = cacheManager.getCache("menu-items");

        if (cache != null) {

            List<MenuItem> menuItems =
                    cache.get("all", List.class);

            if (menuItems != null) {
                System.out.println("Returning cached menuItem.");
                MenuItem fromCache = menuItems.stream().filter(mi->mi.getId().equals(id)).findFirst().get();
                fromCache.setName("from cache");
                return fromCache;
            }
        }

        throw new MenuServiceUnavailableException(
                "Menu Service unavailable and no cached data exists in cache",
                exception
        ); */
    }
}
