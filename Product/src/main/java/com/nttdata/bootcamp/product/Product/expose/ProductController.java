package com.nttdata.bootcamp.product.Product.expose;

import com.nttdata.bootcamp.product.Product.bussiness.ProductService;
import com.nttdata.bootcamp.product.Product.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping(path ="${v1API}/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //list
    @GetMapping()
    public Mono<ResponseEntity<Flux<Product>>> findAll() {
        log.info("findAll>>>>>");
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findAll()));
    }

    //Create
    @PostMapping()
    public Mono<ResponseEntity<Mono<Product>>> create(@RequestBody Product product){
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.create(product)));
    }

    //Detail
    @GetMapping("/{id}")
    public Mono<Product> show(@PathVariable("id") String id) {
        log.info("byId>>>>>");
        return productService.findById(id);
    }

    //Edit
    @PutMapping("")
    public Mono<ResponseEntity<Product>> update(@RequestBody Product product) {
        log.info("update>>>>>");
        return productService.update(product)
                .flatMap(productUpdate -> Mono.just(ResponseEntity.ok(productUpdate)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Product>> delete(@PathVariable("id") String id) {
        log.info("delete>>>>>");
        return productService.remove(id)
                .flatMap(product -> Mono.just(ResponseEntity.ok(product)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
