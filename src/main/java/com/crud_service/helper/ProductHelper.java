package com.crud_service.helper;


import com.crud_service.controllers.dto.ProductDto;
import com.crud_service.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("productHelperFromHelper")
public class ProductHelper {

    @Autowired
    private ModelMapper modelMapper;

    public ProductHelper() {

    }

    public ProductDto toModel(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public Product toEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    public List<ProductDto> toModel(List<Product> products) {
        return products.stream().map(this::toModel).collect(Collectors.toList());
    }

    public List<Product> toEntity(List<ProductDto> productDtos) {
        return productDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public Page<ProductDto> toModel(Page<Product> products) {
        return products.map(this::toModel);
    }
}
