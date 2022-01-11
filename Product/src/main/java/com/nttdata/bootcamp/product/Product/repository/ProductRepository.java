package com.nttdata.bootcamp.product.Product.repository;

import com.nttdata.bootcamp.product.Product.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findAllByActiveIsTrue();
}
