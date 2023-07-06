package com.finalproject.cf.repo;

import com.finalproject.cf.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductByProductId(Long id);

    void deleteByProductId(Long id);
}
