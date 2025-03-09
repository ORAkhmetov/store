package ru.practicum.store.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.store.dto.CreateOrderDto;
import ru.practicum.store.dto.GetOrderDtoInAll;
import ru.practicum.store.model.Order;
import ru.practicum.store.service.OrderService;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public String index(Model model) {
        List<GetOrderDtoInAll> all = orderService.findAll();
        model.addAttribute("orders", all);
        //model.addAttribute("sum", orderService.sumPriceAllOrder(all));
        return "orders";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") int id,
                       @RequestParam(name = "isCreateOrder", defaultValue = "false") boolean isCreateOrder,
                       Model model) {
        GetOrderDtoInAll order = orderService.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("sum", orderService.sumPriceAllOrder(List.of(order)));
        model.addAttribute("isCreateOrder", isCreateOrder);
        return "order";
    }

    @PostMapping("/")
    public String create(@ModelAttribute CreateOrderDto dto) {
        Order saved = orderService.save(dto);
        return "redirect:/order/" + saved.getId() + "?isCreateOrder=true";
    }
}
