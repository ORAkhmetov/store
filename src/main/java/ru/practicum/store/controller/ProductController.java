package ru.practicum.store.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.dto.GetProductDto;
import ru.practicum.store.model.SortType;
import ru.practicum.store.service.ProductService;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String allProducts(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "pageSize", defaultValue = "5") int size,
                              @RequestParam(name = "sort", defaultValue = "NO") String sort,
                              @RequestParam(name = "search", required = false) String searchString) {
        Page<GetProductDto> all = productService.findAll(page, size, SortType.valueOf(sort), searchString);
        List<List<GetProductDto>> partition = ListUtils.partition(all.getContent(), 3);
        model.addAttribute("products", partition);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", all.getTotalPages());
        model.addAttribute("size", size);
        return "main";
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "item";
    }

    @PostMapping
    public String post(@ModelAttribute CreateProductDto productDto) {
        productService.create(productDto);
        return "redirect:/product/";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@PathVariable(name = "id") Long id,
                                @ModelAttribute CreateProductDto productDto) {
        productService.update(productDto, id);
        return "redirect:/product/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        productService.delete(id);
        return "redirect:/product/";
    }
}
