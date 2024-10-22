package com.crud_service.repositories;

import com.crud_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String name);

    boolean existsByName(String name);

    Product findByName(String name);
}
