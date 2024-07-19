package com.ecommerce.customer.customer;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customer) {
        var request = repository.save(customerMapper.toCustomer(customer));
        return request.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        mergeCustomer(customer, request);
        repository.save(customer);
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
        return repository.findAll()
                .stream()
                .map(CustomerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId)
                .map(CustomerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found"));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
