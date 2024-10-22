package com.crud_service.services;

import com.crud_service.entities.Product;
import com.crud_service.exception.RegisterExistsException;
import com.crud_service.repositories.CategoryRepository;
import com.crud_service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getProductsFiltered(String name) {

        if(name == null || name.isEmpty())
            return productRepository.findAll();

        return productRepository.findByNameContaining(name);
    }

    public Product createProduct(Product product) {

        if (productRepository.existsByName(product.getName())) {
            throw new RegisterExistsException("Produto com o nome " + product.getName()
                    + " ja existe na base de dados!");
        }

        if (product.getCategory() != null && product.getCategory().getId() == null) {
            categoryRepository.save(product.getCategory());
        }

        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product oldProduct = productRepository.findByName(product.getName());

        if (oldProduct != null && !oldProduct.getId().equals(id)) {
            throw new RegisterExistsException("Produto com o nome " + product.getName()
                    + " ja existe na base de dados!");
        }


        categoryRepository.save(product.getCategory());

        product.setId(id);
        product.setUpdatedAt(new Date());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}
