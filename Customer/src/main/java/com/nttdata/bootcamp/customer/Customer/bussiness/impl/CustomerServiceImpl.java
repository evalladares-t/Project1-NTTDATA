package com.nttdata.bootcamp.customer.Customer.bussiness.impl;

import com.nttdata.bootcamp.customer.Customer.bussiness.CustomerService;
import com.nttdata.bootcamp.customer.Customer.model.Customer;
import com.nttdata.bootcamp.customer.Customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Date;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Flux<Customer> create(Customer customer) {
        Flux<Customer> customerMono = customerRepository.findCustomerByTypeDocumentAndDocument(customer.getTypeDocument(), customer.getDocument());
        return customerMono.flatMap(p->{
            if(p.getTypeClient().equals("empresarial")){
                customer.setOwner(false);
                customer.setCreatedAt(new Date());
                return customerRepository.save(customer);
            }
            return customerRepository.findById(p.getId());
        }).switchIfEmpty(Mono.defer(() -> {
            customer.setCreatedAt(new Date());
            customer.setOwner(true);
            return customerRepository.save(customer);}));
    }

    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAllByActiveIsTrue();
    }

    @Override
    public Mono<Customer> findById(String customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Mono<Customer> update(Customer customer) {
        return customerRepository.findById(customer.getId())
                .flatMap(productDB -> {
                    return customerRepository.save(customer);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Customer> remove(String customerId) {
        return customerRepository
                .findById(customerId)
                .flatMap(p -> {
                    p.setActive(false);
                    return customerRepository.save(p);
                });
    }
}