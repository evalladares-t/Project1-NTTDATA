package com.nttdata.bootcamp.product.Product.bussiness.impl;

import com.nttdata.bootcamp.product.Product.bussiness.ProductService;
import com.nttdata.bootcamp.product.Product.model.Product;
import com.nttdata.bootcamp.product.Product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Mono<Product> create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAllByActiveIsTrue();
    }

    @Override
    public Mono<Product> findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Mono<Product> update(Product customer) {
        return productRepository.findById(customer.getId())
                .flatMap(productDB -> {
                    return productRepository.save(customer);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Product> remove(String customerId) {
        return productRepository
                .findById(customerId)
                .flatMap(p -> {
                    p.setActive(false);
                    return productRepository.save(p);
                });
    }
}