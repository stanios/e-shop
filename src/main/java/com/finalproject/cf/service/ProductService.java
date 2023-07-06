package com.finalproject.cf.service;

import com.finalproject.cf.dto.ProductDTO;
import com.finalproject.cf.entity.Product;
import com.finalproject.cf.exceptions.EntityNotFoundException;
import com.finalproject.cf.exceptions.NoProductsFoundException;
import com.finalproject.cf.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product insertNewProduct(ProductDTO productDTO) {
        Product product = map(productDTO);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() throws NoProductsFoundException {
        List<Product> products = productRepository.findAll();
        if (products.size() == 0) throw new NoProductsFoundException();
        return products;
    }

    @Transactional
    public void deleteProduct(Long id) throws EntityNotFoundException {
        Product product = productRepository.findProductByProductId(id);
        if (product == null) throw new EntityNotFoundException(Product.class, id);
        productRepository.deleteByProductId(id);
    }

    public Product getProductDetails(Long id) throws EntityNotFoundException {
        Product product = productRepository.findProductByProductId(id);

        if (product == null) throw new EntityNotFoundException(Product.class, id);
        return productRepository.findProductByProductId(id);
    }

    private Product map(ProductDTO productDTO) {
        return new Product(
                productDTO.getProductId(),
                productDTO.getProductName(),
                productDTO.getProductDescription(),
                productDTO.getProductDiscountedPrice(),
                productDTO.getProductActualPrice(),
                productDTO.getProductImages());
    }

}
