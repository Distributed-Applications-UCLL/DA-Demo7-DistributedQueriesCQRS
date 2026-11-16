
package be.ucll.orderhistoryservice.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "orderhistory_item")
public class OrderHistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menu_item_name;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private OrderHistory orderHistory;

    public OrderHistoryItem() {
    }

    public OrderHistoryItem(
            Long id,
            String menu_item_name,
            int quantity,
            OrderHistory orderHistory) {

        this.id = id;
        this.menu_item_name = menu_item_name;
        this.quantity = quantity;
        this.orderHistory = orderHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenu_item_name() {
        return menu_item_name;
    }

    public void setMenu_item_name(String menu_item_name) {
        this.menu_item_name = menu_item_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(OrderHistory orderHistory) {
        this.orderHistory = orderHistory;
    }
}
