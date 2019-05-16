package com.fh.shop.biz;

import com.fh.shop.entity.product.Product;

public interface ProductService {
    void deleteById(Integer id);
    void deleteProductById(Integer id);
    void updateProduct(Product product);
}
