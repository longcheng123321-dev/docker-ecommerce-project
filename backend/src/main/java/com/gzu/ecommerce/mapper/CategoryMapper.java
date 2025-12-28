package com.gzu.ecommerce.mapper;

import com.gzu.ecommerce.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> findAll();
}