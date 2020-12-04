package org.example.dao.customer;

import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    Collection<Customer> readAll();
    void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException;
}
