package hu.garaba.webshop.service;

import hu.garaba.webshop.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.*;

@Service
@SessionScope
public class CartService {
    private final DatabaseService databaseService;
    private Map<Item, Integer> items = new HashMap<>();

    @Autowired
    public CartService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public BigDecimal getSum() {

        // Stream<Entry<Item,Integer>> -> Stream<BigDecimal> -> BigDecimal
        // [..[3],4,5,7,3,[9]..]

        return items.entrySet().stream()          // FUNCTIONAL PROGRAMMING
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public void addItem(int id) {
        Item item = databaseService.fetchItemById(id);
        items.compute(item,  (k, v) -> {
            if (v == null) {
                return 1;
            } else {
                return v + 1;
            }
        });
    }
}
