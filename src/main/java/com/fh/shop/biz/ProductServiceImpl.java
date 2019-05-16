package com.fh.shop.biz;

import com.fh.shop.entity.product.Product;
import com.fh.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductServiceImpl implements ProductService{
    @Resource
    private ProductRepository productRepository;

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    public void deleteProductById(Integer id) {
        productRepository.deleteProductById(id);
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }
}
