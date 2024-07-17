package com.ecommerce.customer.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest customer) {

        if (customer == null) {
            return null;
        }

        return Customer.builder()
                .id(customer.id())
                .firstName(customer.firstName())
                .lastName(customer.lastName())
                .email(customer.email())
                .address(customer.address())
                .build();
    }
}
