package br.com.kaneko.product.repository;

import br.com.kaneko.product.modules.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> findByNameIgnoreCaseContaining(String name);
}
