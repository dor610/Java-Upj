package org.upj.test;

import org.upj.models.Customer;

public class Driver {
    public static void main(String[] args) {

        Customer customer = new Customer("first", "second", "domain.com");
        System.out.println(customer);
    }
}
