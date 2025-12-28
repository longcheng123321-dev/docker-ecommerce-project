package com.gzu.ecommerce.mapper;

import com.gzu.ecommerce.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    int insert(Order order);

    Order findById(Long id);

    List<Order> findBySession(String sessionId);

    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
