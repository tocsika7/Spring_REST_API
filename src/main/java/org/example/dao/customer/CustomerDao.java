package org.example.dao.customer;

import org.example.exception.customer.CustomerInUseException;
import org.example.exception.customer.UnkownCustomerException;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    Collection<Customer> readAll();
    void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException;
    void deleteCustomer(String email) throws UnkownCustomerException, CustomerInUseException;
}
