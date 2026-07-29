package com.mohaymen.store.webapp.web.controller;

import com.mohaymen.store.webapp.service.IProductService;
import com.mohaymen.store.webapp.service.impl.ProductService;
import com.mohaymen.store.webapp.web.response.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final IProductService productService;

    ProductController(IProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String showProductsPage(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        model.addAttribute("pageNo", page);
        return "products";
    }

    @GetMapping("/getProducts")
    public List<ProductDto> products(Authentication authentication, @RequestParam(defaultValue = "1") Integer page) {
        return productService.loadProducts(authentication, page);
    }

}
