package hu.garaba.webshop.service;

import hu.garaba.webshop.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@SessionScope
public class CartService {
    private final DatabaseService databaseService;
    /**
     * A map of items that are added to the cart, where the keys are their ids and the value is the number of them
     */
    private Map<Integer, Integer> items = new HashMap<>();

    @Autowired
    public CartService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Map<Item, Integer> getItems() {
        // Map<int, int> -> Map<Item, int>

        // Map.Pair(2 /*fogkefe*/, 3), Map.Pair(2 /*fogkefe*/, 3), Map.Pair(2 /*fogkefe*/, 3)
        return items.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                e -> databaseService.fetchItemById(e.getKey()),
                                e -> e.getValue()
                        )
                );
    }

    public BigDecimal getSum() {

        // Stream<Entry<Item,Integer>> -> Stream<BigDecimal> -> BigDecimal
        // [..[3],4,5,7,3,[9]..]

        return getItems().entrySet().stream()          // FUNCTIONAL PROGRAMMING
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public void addItem(int id) {
        items.compute(id,  (k, v) -> {
            if (v == null) {
                return 1;
            } else {
                return v + 1;
            }
        });
    }
}
