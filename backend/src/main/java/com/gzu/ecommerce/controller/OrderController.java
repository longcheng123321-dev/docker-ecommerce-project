package com.gzu.ecommerce.controller;

import com.gzu.ecommerce.service.CartService;
import com.gzu.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    // 生成订单（从购物车）
    @PostMapping("/create")
    public String create(HttpSession session, Model model) {
        try {
            Long orderId = orderService.createOrderFromCart(session.getId());
            return "redirect:/order/detail/" + orderId;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cartItems", cartService.getCartItemsWithProduct(session.getId()));
            return "cart";
        }
    }

    // 订单详情
    @GetMapping("/detail/{orderId}")
    public String detail(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderWithDetails(orderId));
        return "order_detail";
    }

    // 订单列表（历史订单）
    @GetMapping("/list")
    public String list(HttpSession session, Model model) {
        model.addAttribute("orders", orderService.getOrdersBySession(session.getId()));
        return "order_list";
    }

    // 模拟付款成功（实际项目会对接支付接口，这里直接改状态）
    @GetMapping("/pay/{orderId}")
    public String pay(@PathVariable Long orderId) {
        orderService.updateStatus(orderId, "已付款");
        return "redirect:/order/detail/" + orderId;
    }
}