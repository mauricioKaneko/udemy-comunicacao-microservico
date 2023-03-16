package br.com.kaneko.product.services;

import br.com.kaneko.product.config.exception.SuccessResponse;
import br.com.kaneko.product.config.exception.ValidationException;
import br.com.kaneko.product.dto.CategoryRequest;
import br.com.kaneko.product.dto.CategoryResponse;
import br.com.kaneko.product.modules.Category;
import br.com.kaneko.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    public Category findById(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("The category ID was not informed");
        }
        return categoryRepository
                .findById(id)
                .orElseThrow(()-> new ValidationException("There's no Category for a given ID"));
    }

    public CategoryResponse findByIdResponse(Integer id){
        return CategoryResponse.of(findById(id));
    }

    public List<CategoryResponse> findByAll(){
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> findByDescription(String description){
        if(ObjectUtils.isEmpty(description)){
            throw new ValidationException("The Category description must be informed");
        }
        return categoryRepository
                .findByDescriptionIgnoreCaseContaining(description)
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public CategoryResponse save(CategoryRequest request){
        validateCategoryNameInformed(request);
        var category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest request, Integer id){
        validateCategoryNameInformed(request);
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("The Category ID must be not informed");
        }
        var category = Category.of(request);
        category.setId(id);
        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    public SuccessResponse delete(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("The Category ID must be not informed");
        }
        if(productService.existsByCategoryId(id)){
            throw new ValidationException("You cannot delete this Category because it's already defined by a product");
        }
        categoryRepository.deleteById(id);
        return SuccessResponse.create("The Category was deleted");
    }

    private void validateCategoryNameInformed(CategoryRequest request){
        if(ObjectUtils.isEmpty(request.getDescription())){
            throw new ValidationException("The category description was not informed");
        }
    }

}
