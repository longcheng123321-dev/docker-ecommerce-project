package com.gzu.ecommerce.service;

import com.gzu.ecommerce.entity.CartItem;
import com.gzu.ecommerce.entity.Product;
import com.gzu.ecommerce.mapper.CartMapper;
import com.gzu.ecommerce.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartMapper cartMapper;

    // 查询所有（用于管理员分页）
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    // 搜索（名称模糊 + 分类过滤，用于前台）
    public List<Product> search(String name, Long categoryId) {
        return productMapper.search(name, categoryId);
    }

    // 根据ID查询
    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    // 新增
    @Transactional
    public void insert(Product product) {
        productMapper.insert(product);
    }

    // 更新
    @Transactional
    public void update(Product product) {
        productMapper.update(product);
    }

    // 删除
    @Transactional
    public void delete(Long id) {
        productMapper.delete(id);
    }

    // 获取当前用户的购物车商品数量（用于首页右上角显示）
    public List<CartItem> getCartItems(String sessionId) {
        return cartMapper.findBySession(sessionId);
    }
}