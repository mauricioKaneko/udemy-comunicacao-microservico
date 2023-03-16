package br.com.kaneko.product.modules;

import br.com.kaneko.product.dto.ProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY", nullable = false)
    private Category category;

    @Column(name = "QUANTITY_AVALIABLE", nullable = false)
    private Integer quantityAvaliable;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

    public static Product of(ProductRequest request,
                             Supplier supplier,
                             Category category){
        return Product
                .builder()
                .name(request.getName())
                .quantityAvaliable(request.getQuantityAvaliable())
                .supplier(supplier)
                .category(category)
                .build();
    }
}
