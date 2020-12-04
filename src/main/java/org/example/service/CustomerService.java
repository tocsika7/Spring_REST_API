package org.example.service;

import org.example.exception.UnkownCustomerException;
import org.example.model.Customer;

import java.util.Collection;

public interface CustomerService {

    Collection<Customer> getAllCustomers();
}
