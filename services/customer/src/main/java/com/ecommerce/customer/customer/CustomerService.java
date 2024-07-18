package com.ecommerce.customer.customer;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customer) {
        var request = customerRepository.save(customerMapper.toCustomer(customer));
        return request.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        mergeCustomer(customer, request);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName()))
            customer.setFirstName(request.firstName());

        if (StringUtils.isNotBlank(request.lastName()))
            customer.setLastName(request.lastName());

        if (StringUtils.isNotBlank(request.email()))
            customer.setEmail(request.email());

        if (request.address() != null)
            customer.setAddress(request.address());
    }

    public List<CustomerResponse> getALlCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::fromCustomer)
                .collect(Collectors.toList());
    }
}
