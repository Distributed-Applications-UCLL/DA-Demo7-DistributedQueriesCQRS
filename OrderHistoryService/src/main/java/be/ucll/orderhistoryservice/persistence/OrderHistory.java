
package be.ucll.orderhistoryservice.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orderhistory")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String customerName;
    private String restaurantName;
    private BigDecimal total;

    @OneToMany(
            mappedBy = "orderHistory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderHistoryItem> items = new ArrayList<>();

    public OrderHistory() {
    }

    public OrderHistory(
            List<OrderHistoryItem> items,
            BigDecimal total,
            String restaurantName,
            Long customerId,
            String customerName) {

        this.total = total;
        this.restaurantName = restaurantName;
        this.customerId = customerId;
        this.customerName = customerName;

        setItems(items);
    }

    public void addItem(OrderHistoryItem item) {
        items.add(item);
        item.setOrderHistory(this);
    }

    public void setItems(List<OrderHistoryItem> items) {
        this.items.clear();

        if (items != null) {
            for (OrderHistoryItem item : items) {
                addItem(item);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<OrderHistoryItem> getItems() {
        return items;
    }
}
