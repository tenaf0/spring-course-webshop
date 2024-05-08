package hu.garaba.webshop.service;

import hu.garaba.webshop.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private final DatabaseService databaseService;
    private List<Item> items = new ArrayList<>();

    @Autowired
    public CartService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(int id) {
        Item item = databaseService.fetchItemById(id);
        items.add(item);
    }
}
