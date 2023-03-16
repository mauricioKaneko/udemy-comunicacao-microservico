package br.com.kaneko.product.controller;

import br.com.kaneko.product.config.exception.SuccessResponse;
import br.com.kaneko.product.dto.ProductRequest;
import br.com.kaneko.product.dto.ProductResponse;
import br.com.kaneko.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponse> findAll(){
        return productService.findByAll();
    }

    @GetMapping("{id}")
    public ProductResponse findById(@PathVariable Integer id){
        return productService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<ProductResponse> findByDescription(@PathVariable String name){
        return productService.findByName(name);
    }

    @GetMapping("category/{category}")
    public List<ProductResponse> findByCategory(@PathVariable Integer category){
       return productService.findByCategoryID(category);
    }

    @GetMapping("supplier/{supplier}")
    public List<ProductResponse> findBySupplier(@PathVariable Integer supplier){
        return productService.findByCategoryID(supplier);
    }

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request){
        return productService.save(request);
    }

    @PutMapping("{id}")
    public ProductResponse update(@RequestBody ProductRequest request,
                                   @PathVariable Integer id){
        return productService.update(request,id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id){
        return productService.delete(id);
    }
}
