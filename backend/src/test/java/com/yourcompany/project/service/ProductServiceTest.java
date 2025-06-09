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
import org.modelmapper.ModelMapper;

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

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Test Product", new BigDecimal("99.99"));
        productDTO = new ProductDTO(1L, "Test Product", new BigDecimal("99.99"));

        when(modelMapper.map(any(ProductDTO.class), eq(Product.class))).thenReturn(product);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);
    }

    @Test
    void create_ShouldReturnCreatedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.create(productDTO);

        assertNotNull(result);
        assertEquals(productDTO.name(), result.name());
        assertEquals(productDTO.price(), result.price());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void listAll_ShouldReturnAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> result = productService.listAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void getById_WhenProductExists_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getById(1L);

        assertNotNull(result);
        assertEquals(productDTO.name(), result.name());
        assertEquals(productDTO.price(), result.price());
        verify(productRepository).findById(1L);
    }

    @Test
    void getById_WhenProductDoesNotExist_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getById(1L));
        verify(productRepository).findById(1L);
    }

    @Test
    void delete_WhenProductExists_ShouldDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        assertDoesNotThrow(() -> productService.delete(1L));
        verify(productRepository).existsById(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void delete_WhenProductDoesNotExist_ShouldThrowException() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.delete(1L));
        verify(productRepository).existsById(1L);
        verify(productRepository, never()).deleteById(any());
    }
}