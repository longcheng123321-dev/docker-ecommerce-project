package com.gzu.ecommerce.mapper;

import com.gzu.ecommerce.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> findAll();

    List<Product> search(@Param("name") String name, @Param("categoryId") Long categoryId);

    Product findById(Long id);

    int insert(Product product);

    int update(Product product);

    int delete(Long id);

    // 扣库存（减少）
    int decreaseStock(@Param("id") Long id, @Param("quantity") int quantity);
}
