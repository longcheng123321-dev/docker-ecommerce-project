package com.gzu.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String sessionId;
    private BigDecimal totalAmount;
    private String status; // 待付款、已付款、已发货、已完成
    private LocalDateTime createTime;
}
