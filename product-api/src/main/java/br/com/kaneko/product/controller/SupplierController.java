package br.com.kaneko.product.controller;

import br.com.kaneko.product.config.exception.SuccessResponse;
import br.com.kaneko.product.dto.SupplierRequest;
import br.com.kaneko.product.dto.SupplierResponse;
import br.com.kaneko.product.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<SupplierResponse> findAll(){
        return supplierService.findByAll();
    }

    @GetMapping("{id}")
    public SupplierResponse findById(@PathVariable Integer id){
        return supplierService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<SupplierResponse> findByDescription(@PathVariable String name){

        return supplierService.findByName(name);
    }

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest request){
        System.out.println(request.getName());
        return supplierService.save(request);
    }

    @PutMapping("{id}")
    public SupplierResponse update(@RequestBody SupplierRequest request,
                                  @PathVariable Integer id){
        return supplierService.update(request,id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id){
        return supplierService.delete(id);
    }


}
