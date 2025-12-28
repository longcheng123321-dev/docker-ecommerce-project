package com.gzu.ecommerce.mapper;

import com.gzu.ecommerce.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {

    List<CartItem> findBySession(String sessionId);

    CartItem findBySessionAndProduct(@Param("sessionId") String sessionId, @Param("productId") Long productId);

    int insert(CartItem cartItem);

    int update(CartItem cartItem);

    int deleteBySessionAndProduct(@Param("sessionId") String sessionId, @Param("productId") Long productId);

    int clearBySession(String sessionId);
}
