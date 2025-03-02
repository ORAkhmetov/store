package ru.practicum.store.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.store.dto.GetCartDto;
import ru.practicum.store.service.CartService;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/")
    public String index(Model model) {
        List<GetCartDto> allCarts = cartService.findAllCarts();
        model.addAttribute("carts", allCarts);
        return "cart";
    }

    @PostMapping("/updateQuantity/{id}")
    public String update(@PathVariable(name = "id") long id,
                         @RequestParam String action) {
        switch (action) {
            case "plus" -> cartService.increaseQuantity(id);
            case "minus" -> cartService.decreaseQuantity(id);
            case "delete" -> cartService.deleteCart(id);
        }
        cartService.increaseQuantity(id);
        return "redirect:/cart/";
    }
}
