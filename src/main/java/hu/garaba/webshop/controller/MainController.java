package hu.garaba.webshop.controller;

import hu.garaba.webshop.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class MainController {
    private DatabaseService databaseService;

    @Autowired
    public MainController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        record User(String name, boolean admin) {}

        model.addAttribute("user", new Random().nextBoolean() ? new User("jani", true) : null);
        model.addAttribute("messageCount", 10);
        model.addAttribute("items", databaseService.fetchItems());

        return "index"; // View
    }
}
