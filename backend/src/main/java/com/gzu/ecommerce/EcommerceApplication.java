package com.gzu.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
        System.out.println("\n" +
                "=============================================\n" +
                "   简单电商管理系统启动成功！\n" +
                "   访问地址：http://localhost:8080\n" +
                "   管理员商品管理：http://localhost:8080/admin/products\n" +
                "=============================================\n");
    }
}
