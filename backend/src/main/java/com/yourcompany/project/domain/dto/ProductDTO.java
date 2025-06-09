package com.yourcompany.project.domain.dto;

import com.yourcompany.project.domain.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
        private Long id;

        @NotBlank(message = "Product name is required")
        private String name;

        private String description;

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        private BigDecimal price;

        private Integer stock;

        public static ProductDTO fromEntity(Product product) {
                ProductDTO dto = new ProductDTO();
                dto.setId(product.getId());
                dto.setName(product.getName());
                dto.setDescription(product.getDescription());
                dto.setPrice(product.getPrice());
                dto.setStock(product.getStock());
                return dto;
        }

        public Product toEntity() {
                Product product = new Product();
                product.setId(this.id);
                product.setName(this.name);
                product.setDescription(this.description);
                product.setPrice(this.price);
                product.setStock(this.stock);
                return product;
        }
}