package br.com.kaneko.product.services;

import br.com.kaneko.product.config.exception.SuccessResponse;
import br.com.kaneko.product.config.exception.ValidationException;
import br.com.kaneko.product.dto.SupplierRequest;
import br.com.kaneko.product.dto.SupplierResponse;
import br.com.kaneko.product.modules.Supplier;
import br.com.kaneko.product.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    public Supplier findById(Integer id){
        return supplierRepository
                .findById(id)
                .orElseThrow(()-> new ValidationException("There's no Supplier for a given ID"));
    }

    public SupplierResponse findByIdResponse(Integer id){

        return SupplierResponse.of(findById(id));
    }

    public List<SupplierResponse> findByAll(){
        return supplierRepository
                .findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByName(String name){
        if(ObjectUtils.isEmpty(name)){
            throw new ValidationException("The Supplier description must be informed");
        }
        return supplierRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public SupplierResponse save(SupplierRequest request){
        validateCategoryNameInformed(request);
        var supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    public SupplierResponse update(SupplierRequest request, Integer id){
        validateCategoryNameInformed(request);
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("The Category ID must be not informed");
        }
        var supplier = Supplier.of(request);
        supplier.setId(id);
        supplierRepository.save(supplier);
        return SupplierResponse.of(supplier);
    }

    public SuccessResponse delete(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("The Supplier ID must be not informed");
        }
        if(productService.existsBySupplierId(id)){
            throw new ValidationException("You cannot delete this supplier because it's already defined by a product");
        }
        supplierRepository.deleteById(id);
        return SuccessResponse.create("The Supplier was deleted");
    }


    private void validateCategoryNameInformed(SupplierRequest request){
        if(ObjectUtils.isEmpty(request.getName())){
            throw new ValidationException("The Supplier description was not informed");
        }
    }

}
