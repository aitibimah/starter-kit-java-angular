package com.yourcompany.project.controller;

import com.yourcompany.project.domain.dto.ApiDataResponse;
import com.yourcompany.project.domain.dto.ProductDTO;
import com.yourcompany.project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product management APIs")
public class ProductController extends AbstractController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Create a new product", description = "Creates a new product. Requires ADMIN role.")
    public ResponseEntity<ApiDataResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return created(productService.createProduct(productDTO));
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all products.")
    public ResponseEntity<ApiDataResponse<List<ProductDTO>>> getAllProducts() {
        return ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID.")
    public ResponseEntity<ApiDataResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        return ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<ProductDTO>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        return ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Delete product", description = "Deletes a product by its ID. Requires ADMIN role.")
    public ResponseEntity<ApiDataResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return noContent();
    }
}
