package com.ecommerce.customer.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customer) {
        var request = customerRepository.save(customerMapper.toCustomer(customer));
        return request.getId();
    }
}
