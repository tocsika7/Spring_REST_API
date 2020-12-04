package org.example.service;

import org.example.exception.UnkownCustomerException;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Customer;

import java.util.Collection;

public interface CustomerService {

    Collection<Customer> getAllCustomers();
    void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException;
}
