package com.gzu.ecommerce.service;

import com.gzu.ecommerce.entity.*;
import com.gzu.ecommerce.mapper.OrderDetailMapper;
import com.gzu.ecommerce.mapper.OrderMapper;
import com.gzu.ecommerce.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartService cartService;

    // 从购物车生成订单（事务 + 库存校验 + 扣库存）
    @Transactional
    public Long createOrderFromCart(String sessionId) {
        List<Map<String, Object>> cartWithProduct = cartService.getCartItemsWithProduct(sessionId);
        if (cartWithProduct.isEmpty()) {
            throw new RuntimeException("购物车为空");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Map<String, Object> item : cartWithProduct) {
            CartItem cartItem = (CartItem) item.get("cartItem");
            Product product = (Product) item.get("product");
            int qty = cartItem.getQuantity();

            if (product.getStock() < qty) {
                throw new RuntimeException("商品《" + product.getName() + "》库存不足");
            }
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(qty)));
        }

        // 创建订单
        Order order = new Order();
        order.setSessionId(sessionId);
        order.setTotalAmount(totalAmount);
        order.setStatus("待付款");
        orderMapper.insert(order);  // 获取自增ID

        // 创建订单明细并扣库存
        for (Map<String, Object> item : cartWithProduct) {
            CartItem cartItem = (CartItem) item.get("cartItem");
            Product product = (Product) item.get("product");

            OrderDetail detail = new OrderDetail();
            detail.setOrderId(order.getId());
            detail.setProductId(product.getId());
            detail.setQuantity(cartItem.getQuantity());
            detail.setPrice(product.getPrice());
            orderDetailMapper.insert(detail);

            // 扣库存
            productMapper.decreaseStock(product.getId(), cartItem.getQuantity());
        }

        // 清空购物车
        cartService.clearCart(sessionId);

        return order.getId();
    }

    // 更新订单状态（如付款、发货）
    @Transactional
    public void updateStatus(Long orderId, String status) {
        orderMapper.updateStatus(orderId, status);
    }

    // 查询订单列表（按session）
    public List<Order> getOrdersBySession(String sessionId) {
        return orderMapper.findBySession(sessionId);
    }

    // 查询订单详情（带明细）
    public Map<String, Object> getOrderWithDetails(Long orderId) {
        Order order = orderMapper.findById(orderId);
        List<OrderDetail> details = orderDetailMapper.findByOrderId(orderId);

        List<Map<String, Object>> detailList = details.stream().map(d -> {
            Product p = productMapper.findById(d.getProductId());
            Map<String, Object> map = new HashMap<>();
            map.put("detail", d);
            map.put("product", p);
            map.put("subtotal", p.getPrice().multiply(BigDecimal.valueOf(d.getQuantity())));
            return map;
        }).toList();

        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("details", detailList);
        return result;
    }
}
