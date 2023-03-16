package br.com.kaneko.product.services;

import br.com.kaneko.product.config.exception.SuccessResponse;
import br.com.kaneko.product.config.exception.ValidationException;
import br.com.kaneko.product.dto.ProductRequest;
import br.com.kaneko.product.dto.ProductResponse;
import br.com.kaneko.product.modules.Product;
import br.com.kaneko.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final Integer ZERO= 0;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    public Product findById(Integer id){
        return productRepository
                .findById(id)
                .orElseThrow(()-> new ValidationException("There's no Product for a given ID"));
    }

    public ProductResponse findByIdResponse(Integer id){
        return ProductResponse.of(findById(id));
    }

    public List<ProductResponse> findByAll(){
        return productRepository
                .findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByName(String name){
        if(ObjectUtils.isEmpty(name)){
            throw new ValidationException("The Product description must be informed");
        }
        return productRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierID(Integer supplierId){
        if(ObjectUtils.isEmpty(supplierId)){
            throw new ValidationException("The Product's Supplier id must be informed");
        }
        return productRepository
                .findBySupplierId(supplierId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryID(Integer categoryId){
        if(ObjectUtils.isEmpty(categoryId)){
            throw new ValidationException("The Product's Supplier id must be informed");
        }
        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
    
    public ProductResponse save(ProductRequest request){
        validateProductDataInformed(request);
        validateCategoryandSupplierIDInformed(request);
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());

        var product = productRepository.save(Product.of(request,supplier,category));
        return ProductResponse.of(product);
    }


    public ProductResponse update(ProductRequest request, Integer id){
        validateProductDataInformed(request);
        validateCategoryandSupplierIDInformed(request);
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("The Product ID must be not informed");
        }
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = Product.of(request,supplier,category);
        product.setId(id);
        productRepository.save(product);
        return ProductResponse.of(product);
    }



    public SuccessResponse delete(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("The Product ID must be not informed");
        }
        productRepository.deleteById(id);
        return SuccessResponse.create("The Product was deleted");
    }

    public Boolean existsByCategoryId(Integer categoryId){
        return productRepository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId(Integer supplierId){
        return productRepository.existsBySupplierId(supplierId);
    }

    private void validateProductDataInformed(ProductRequest request){
        if(ObjectUtils.isEmpty(request.getName())){
            throw new ValidationException("The Product name was not informed");
        }
        if(ObjectUtils.isEmpty(request.getQuantityAvaliable())){
            throw new ValidationException("The Product quantity was not informed");
        }
        if(request.getQuantityAvaliable()<=ZERO){
            throw new ValidationException("The quantity should not less or equal to zero");
        }
    }

    private void validateCategoryandSupplierIDInformed(ProductRequest request){
        if(ObjectUtils.isEmpty(request.getCategoryId())){
            throw new ValidationException("The Category description was not informed");
        }
        if(ObjectUtils.isEmpty(request.getSupplierId())){
            throw new ValidationException("The Supplier description was not informed");
        }
    }



}
