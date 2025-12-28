package com.gzu.ecommerce.mapper;

import com.gzu.ecommerce.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    int insert(OrderDetail orderDetail);

    List<OrderDetail> findByOrderId(Long orderId);
}
