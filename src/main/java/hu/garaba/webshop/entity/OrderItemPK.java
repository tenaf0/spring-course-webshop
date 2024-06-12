package hu.garaba.webshop.entity;

import java.io.Serial;
import java.io.Serializable;

public class OrderItemPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    private Order order;
    private Item item;

    public OrderItemPK() {
    }

    public OrderItemPK(Order order, Item item) {
        this.order = order;
        this.item = item;
    }
}
