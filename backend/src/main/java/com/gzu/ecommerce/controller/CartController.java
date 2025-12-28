package com.gzu.ecommerce.controller;

import com.gzu.ecommerce.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 查看购物车
    @GetMapping
    public String cart(HttpSession session, Model model) {
        String sessionId = session.getId();
        model.addAttribute("cartItems", cartService.getCartItemsWithProduct(sessionId));
        return "cart"; // templates/cart.html
    }

    // 加入购物车
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session) {
        cartService.addToCart(session.getId(), productId, quantity);
        return "redirect:/";
    }

    // 更新数量
    @PostMapping("/update")
    public String update(
            @RequestParam Long productId,
            @RequestParam int quantity,
            HttpSession session) {
        cartService.updateQuantity(session.getId(), productId, quantity);
        return "redirect:/cart";
    }

    // 删除商品
    @GetMapping("/remove/{productId}")
    public String remove(@PathVariable Long productId, HttpSession session) {
        cartService.removeItem(session.getId(), productId);
        return "redirect:/cart";
    }

    // 清空购物车（下单后会自动清空，这里也提供手动清空）
    @GetMapping("/clear")
    public String clear(HttpSession session) {
        cartService.clearCart(session.getId());
        return "redirect:/cart";
    }
}