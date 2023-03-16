package br.com.kaneko.product.controller;

import br.com.kaneko.product.config.exception.SuccessResponse;
import br.com.kaneko.product.dto.CategoryRequest;
import br.com.kaneko.product.dto.CategoryResponse;
import br.com.kaneko.product.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> findAll(){
        return categoryService.findByAll();
    }

    @GetMapping("{id}")
    public CategoryResponse findById(@PathVariable Integer id){
        return categoryService.findByIdResponse(id);
    }

    @GetMapping("description/{description}")
    public List<CategoryResponse> findByDescription(@PathVariable String description){
        return categoryService.findByDescription(description);
    }

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request){
        System.out.println(request.getDescription());
        return categoryService.save(request);
    }

    @PutMapping("{id}")
    public CategoryResponse update(@RequestBody CategoryRequest request,
                                   @PathVariable Integer id){
        return categoryService.update(request,id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id){
        return categoryService.delete(id);
    }
}
