package hu.garaba.webshop.controller;

import hu.garaba.webshop.entity.Item;
import hu.garaba.webshop.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/insertItem")
    public String insertItem(@RequestParam String name, @RequestParam BigDecimal price, Model model) {
        // int == 2^32
        // Integer == BOXED
        /*
        class Integer {
            public final int value;

            public Integer(int val) {
                this.value = val;
            }
        }

        Integer x = new Integer(3)
        Integer y = null
        x.val;

        List<Integer> list = new ArrayList<>();
        list.add(3); // type = int
                -> list.add(new Integer(3)); // auto-boxing auto-unboxing
        int x = list.get(0);
                -> list.get(0).val;

         +-128
         */

        // name=tablet&price=13241.2

        Item item = new Item();
        item.setName(name);
        item.setPrice(price);

        databaseService.insertItem(item);

        return "itemInsertSuccess";
    }
}
