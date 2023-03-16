package br.com.kaneko.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    @JsonProperty("quantity_avaliable")
    private Integer quantityAvaliable;
    private Integer supplierId;
    private Integer categoryId;

}
