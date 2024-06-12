package hu.garaba.webshop.controller;

import hu.garaba.webshop.entity.Order;
import hu.garaba.webshop.service.CartService;
import hu.garaba.webshop.repository.OrderRepository;
import hu.garaba.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository, CartService cartService, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/new")
    public String newOrder(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("sum", cartService.getSum());

        return "order/newOrder";
    }

    @PostMapping("/new")
    public String newOrder(@RequestParam String name, @RequestParam String address, Model model) {
        if (name.isEmpty() || address.isEmpty()) {
            model.addAttribute("formError", true);
            return newOrder(model);
        }

        long orderId = orderService.createOrder(name, address, cartService.getItems());

        return "redirect:/order/" + orderId;
    }


    @GetMapping("/{orderId}")
    public String getOrderById(@PathVariable long orderId, Model model) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        model.addAttribute("order", order.get());

        return "order/orderView";
    }
}
