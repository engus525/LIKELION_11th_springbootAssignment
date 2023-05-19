package likelion.springbootkdh.controller;


import java.util.List;
import likelion.springbootkdh.domain.Item;
import likelion.springbootkdh.service.ItemService;
import likelion.springbootkdh.service.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemServiceImpl itemServiceImpl) {
        this.itemService = itemServiceImpl;
    }

    @GetMapping("new")
    public String createForm(Model model) {
        model.addAttribute("itemForm", new Item());
        return "items/createItemForm";
    }

    @PostMapping("new")
    public String create(Item item) {
        itemService.save(item);
        return "redirect:/";
    }

    @GetMapping("")
    public String findAll(Model model) {
        List<Item> itemList = this.itemService.findAll();
        model.addAttribute("itemList", itemList);
        return "items/itemList";
    }

    @GetMapping("{id}/update")
    public String updateItemForm(@PathVariable("id") Long itemId, Model model) {
        Item item = this.itemService.findById(itemId);
        model.addAttribute("itemFormUpdate", item);
        return "items/updateItemForm";
    }

    @PostMapping({"update"})
    public String update(@RequestParam Long id, @ModelAttribute("form") Item form) {
        this.itemService.update(id, form);
        return "redirect:/items";
    }
}

