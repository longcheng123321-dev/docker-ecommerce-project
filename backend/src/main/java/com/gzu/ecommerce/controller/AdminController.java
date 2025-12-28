package com.gzu.ecommerce.controller;

import com.gzu.ecommerce.entity.Category;
import com.gzu.ecommerce.entity.Product;
import com.gzu.ecommerce.service.CategoryService;
import com.gzu.ecommerce.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // 商品管理列表
    @GetMapping("/products")
    public String productList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productService.findAll();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("products", pageInfo.getList());
        model.addAttribute("categories", categories);
        return "admin/product_list";
    }

    // 新增/编辑页面
    @GetMapping("/product/edit")
    public String editForm(@RequestParam(required = false) Long id, Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        if (id != null) {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
        }
        return "admin/product_edit";
    }

    // 保存（新增或更新）
    @PostMapping("/product/save")
    public String save(Product product) {
        if (product.getId() == null) {
            productService.insert(product);
        } else {
            productService.update(product);
        }
        return "redirect:/admin/products";
    }

    // 删除
    @GetMapping("/product/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}
