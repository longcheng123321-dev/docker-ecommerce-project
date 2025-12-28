-- 第1步：创建数据库（可以先手动创建，或者执行这句）
CREATE DATABASE IF NOT EXISTS ecommerce DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 第2步：使用数据库
USE ecommerce;

-- 第3步：创建分类表
CREATE TABLE category (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE
);

-- 第4步：创建商品表
CREATE TABLE product (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(200) NOT NULL,
description TEXT,
price DECIMAL(10,2) NOT NULL,
stock INT NOT NULL DEFAULT 0,
category_id BIGINT,
image_url VARCHAR(500),
FOREIGN KEY (category_id) REFERENCES category(id)
);

-- 第5步：创建购物车表
CREATE TABLE cart_item (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
session_id VARCHAR(100) NOT NULL,
product_id BIGINT NOT NULL,
quantity INT NOT NULL DEFAULT 1,
FOREIGN KEY (product_id) REFERENCES product(id)
);

-- 第6步：创建订单表
CREATE TABLE orders (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
session_id VARCHAR(100) NOT NULL,
total_amount DECIMAL(10,2) NOT NULL,
tatus VARCHAR(20) NOT NULL DEFAULT '待付款',
create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 第7步：创建订单明细表
CREATE TABLE order_detail (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
order_id BIGINT NOT NULL,
product_id BIGINT NOT NULL,
quantity INT NOT NULL,
price DECIMAL(10,2) NOT NULL,
FOREIGN KEY (order_id) REFERENCES orders(id),
FOREIGN KEY (product_id) REFERENCES product(id)
);

-- 第8步：插入测试数据
INSERT INTO category (name) VALUES ('Electronics'), ('Clothing'), ('Books');

INSERT INTO product (name, description, price, stock, category_id, image_url) VALUES
('iPhone 15', 'Latest Apple smartphone', 7999.00, 50, 1, 'https://www.apple.com/newsroom/images/2023/09/apple-debuts-iphone-15-and-iphone-15-plus/tile/Apple-iPhone-15-lineup-hero-230912.jpg.landing-big_2x.jpg'),
('Summer Cotton T-Shirt', 'Comfortable and breathable', 99.00, 200, 2, 'https://cdn01.pinkoi.com/product/39j6gzNb/01JYBF0ZS22XWJ2T9D1VR3SSCP/640x530.jpg'),
('Thinking in Java', 'Classic Java programming book', 128.00, 100, 3, 'https://m.media-amazon.com/images/I/41fH4cAKL1L.jpg');