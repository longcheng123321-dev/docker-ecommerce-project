package com.gzu.ecommerce.service;

import com.gzu.ecommerce.entity.CartItem;
import com.gzu.ecommerce.entity.Product;
import com.gzu.ecommerce.mapper.CartMapper;
import com.gzu.ecommerce.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    // 添加到购物车（数量累加）
    @Transactional
    public void addToCart(String sessionId, Long productId, int quantity) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        if (product.getStock() < quantity) {
            throw new RuntimeException("库存不足，仅剩 " + product.getStock() + " 件");
        }

        CartItem existing = cartMapper.findBySessionAndProduct(sessionId, productId);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartMapper.update(existing);
        } else {
            CartItem item = new CartItem();
            item.setSessionId(sessionId);
            item.setProductId(productId);
            item.setQuantity(quantity);
            cartMapper.insert(item);
        }
    }

    // 更新数量（可增可减）
    @Transactional
    public void updateQuantity(String sessionId, Long productId, int quantity) {
        if (quantity <= 0) {
            removeItem(sessionId, productId);
            return;
        }
        CartItem item = cartMapper.findBySessionAndProduct(sessionId, productId);
        if (item != null) {
            Product product = productMapper.findById(productId);
            if (product.getStock() < quantity) {
                throw new RuntimeException("库存不足");
            }
            item.setQuantity(quantity);
            cartMapper.update(item);
        }
    }

    // 删除单项
    @Transactional
    public void removeItem(String sessionId, Long productId) {
        cartMapper.deleteBySessionAndProduct(sessionId, productId);
    }

    // 清空购物车
    @Transactional
    public void clearCart(String sessionId) {
        cartMapper.clearBySession(sessionId);
    }

    // 查询购物车列表（带商品详情）
    public List<Map<String, Object>> getCartItemsWithProduct(String sessionId) {
        List<CartItem> items = cartMapper.findBySession(sessionId);
        return items.stream().map(item -> {
            Product p = productMapper.findById(item.getProductId());
            Map<String, Object> map = new HashMap<>();
            map.put("cartItem", item);
            map.put("product", p);
            map.put("subtotal", p.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())));
            return map;
        }).toList();
    }
}
