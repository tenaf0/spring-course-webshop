package hu.garaba.webshop.service;

import hu.garaba.webshop.entity.Item;
import hu.garaba.webshop.entity.Order;
import hu.garaba.webshop.entity.OrderItem;
import hu.garaba.webshop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public long createOrder(String name, String address, Map<Item, Integer> items) {
        var order = new Order();
        order.setName(name);
        order.setAddress(address);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        order.setOrderItems(orderItems);

        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(entry.getKey());
            orderItem.setNumber(entry.getValue());
            orderItem.setUnitPrice(entry.getKey().getPrice());

            orderItems.add(orderItem);
        }

        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }
}
