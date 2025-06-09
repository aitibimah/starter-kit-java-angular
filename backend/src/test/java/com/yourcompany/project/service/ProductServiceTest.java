package com.yourcompany.project.service;

import com.yourcompany.project.domain.Product;
import com.yourcompany.project.domain.dto.ProductDTO;
import com.yourcompany.project.exception.ResourceNotFoundException;
import com.yourcompany.project.repository.ProductRepository;
import com.yourcompany.project.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("99.99"));
        product.setStock(10);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(new BigDecimal("99.99"));
        productDTO.setStock(10);
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getPrice(), result.getPrice());
        assertEquals(productDTO.getDescription(), result.getDescription());
        assertEquals(productDTO.getStock(), result.getStock());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getPrice(), result.getPrice());
        assertEquals(productDTO.getDescription(), result.getDescription());
        assertEquals(productDTO.getStock(), result.getStock());
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_WhenProductDoesNotExist_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository).findById(1L);
    }

    @Test
    void updateProduct_WhenProductExists_ShouldReturnUpdatedProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.updateProduct(1L, productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getPrice(), result.getPrice());
        assertEquals(productDTO.getDescription(), result.getDescription());
        assertEquals(productDTO.getStock(), result.getStock());
        verify(productRepository).existsById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_WhenProductDoesNotExist_ShouldThrowException() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(1L, productDTO));
        verify(productRepository).existsById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteProduct_WhenProductExists_ShouldDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository).existsById(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_WhenProductDoesNotExist_ShouldThrowException() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository).existsById(1L);
        verify(productRepository, never()).deleteById(any());
    }
}