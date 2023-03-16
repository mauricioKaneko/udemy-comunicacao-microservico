package br.com.kaneko.product.repository;

import br.com.kaneko.product.modules.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByDescriptionIgnoreCaseContaining(String description);
}
