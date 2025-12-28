package com.gzu.ecommerce.controller;

import com.gzu.ecommerce.entity.Category;
import com.gzu.ecommerce.entity.Product;
import com.gzu.ecommerce.service.CategoryService;
import com.gzu.ecommerce.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // 首页：商品列表（支持搜索 + 分页）
    @GetMapping("/")
    public String index(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "9") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            Model model,
            HttpSession session) {

        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productService.search(name, categoryId);
        PageInfo<Product> pageInfo = new PageInfo<>(products);

        List<Category> categories = categoryService.findAll();

        model.addAttribute("products", pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("categories", categories);
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);

        // 购物车数量（用于右上角显示）
        model.addAttribute("cartItems", productService.getCartItems(session.getId()));

        return "index"; // templates/index.html
    }

    // 商品详情
    @GetMapping("/product/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("cartItems", productService.getCartItems(session.getId()));
        return "product_detail";
    }
}