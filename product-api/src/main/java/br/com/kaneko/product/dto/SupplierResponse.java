package br.com.kaneko.product.dto;

import br.com.kaneko.product.modules.Supplier;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SupplierResponse {
    private Integer id;
    private String name;

    public static SupplierResponse of(Supplier supplier){
        var response = new SupplierResponse();
        BeanUtils.copyProperties(supplier, response);
        return response;
    }
}
