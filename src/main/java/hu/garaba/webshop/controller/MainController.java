package hu.garaba.webshop.controller;

import hu.garaba.webshop.entity.Item;
import hu.garaba.webshop.service.CartService;
import hu.garaba.webshop.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class MainController {
    private final CartService cartService;
    private final DatabaseService databaseService;

    @Autowired
    public MainController(CartService cartService, DatabaseService databaseService) {
        this.cartService = cartService;
        this.databaseService = databaseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", databaseService.fetchItems());
        model.addAttribute("categories", databaseService.fetchCategories());

        return "index"; // View
    }

    @GetMapping("/item/{id}")
    public String itemView(@PathVariable int id, Model model) {
        model.addAttribute("item", databaseService.fetchItemById(id));

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

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String q, Model model) {
        List<Item> items = databaseService.searchItems(q);
        model.addAttribute("searchTerm", q);
        model.addAttribute("searchResults", items);

        return "searchResult";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("sum", cartService.getSum());

        return "cart";
    }

    @PostMapping("/cart")
    public RedirectView addItemToCart(@RequestParam int id, Model model) {
        cartService.addItem(id);

        // Also works as returning this string: "redirect:/cart"
        return new RedirectView("cart");
    }
}
