package com.yourcompany.project.service.impl;

import com.yourcompany.project.domain.Product;
import com.yourcompany.project.domain.dto.ProductDTO;
import com.yourcompany.project.exception.ResourceNotFoundException;
import com.yourcompany.project.repository.ProductRepository;
import com.yourcompany.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
            .map(ProductDTO::fromEntity)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    @Cacheable(value = "products")
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductDTO::fromEntity)
            .toList();
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO createProduct(ProductDTO productDTO) {
        return ProductDTO.fromEntity(
            productRepository.save(productDTO.toEntity()));
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        Product product = productDTO.toEntity();
        product.setId(id);
        return ProductDTO.fromEntity(productRepository.save(product));
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
