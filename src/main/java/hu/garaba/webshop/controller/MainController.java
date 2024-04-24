package hu.garaba.webshop.controller;

import hu.garaba.webshop.entity.Item;
import hu.garaba.webshop.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class MainController {
    private final DatabaseService databaseService;

    @Autowired
    public MainController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", databaseService.fetchItems());

        return "index"; // View
    }

    @GetMapping("/item/{id}")
    public String itemView(@PathVariable int id, Model model) {
        model.addAttribute("id", id);

        return "itemView";
    }

    @PostMapping("/")
    public String insertItem(@RequestParam String name, @RequestParam BigDecimal price, Model model) {
        model.addAttribute("name", name);

        databaseService.insertItem(new Item(-1, name, price));

        return "itemInsertSuccess";
    }
}
