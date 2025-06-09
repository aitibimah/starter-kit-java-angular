package com.yourcompany.project.service;

import com.yourcompany.project.domain.dto.ProductDTO;
import com.yourcompany.project.exception.ResourceNotFoundException;
import com.yourcompany.project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "products", key = "#id")
    public ProductDTO getById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Cacheable(value = "products")
    public List<ProductDTO> listAll() {
        return productRepository.findAll().stream()
                .map(ProductDTO::fromEntity)
                .toList();
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO create(ProductDTO productDTO) {
        return ProductDTO.fromEntity(
                productRepository.save(productDTO.toEntity()));
    }

    @CacheEvict(value = "products", allEntries = true)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
