package org.upj.service;

import org.upj.models.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomerService {

    public static CustomerService customerService;

    static {
        CustomerService.customerService = new CustomerService();
    }

    private Collection<Customer> customerList;

    public CustomerService(){
        this.customerList = new ArrayList<>();
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = getCustomer(email);
        if (customer != null) {
            throw new IllegalArgumentException("The email has already been registered");
        }
        customerList.add(new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String email) {
        return this.customerList.stream().filter(customer -> customer.getEmail().equals(email)).findFirst().orElse(null);
    }

    public Collection<Customer> getAllCustomers() {
        return this.customerList;
    }
}
