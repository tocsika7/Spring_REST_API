package org.example.service.customer;

import org.example.exception.address.UnknownAddressException;
import org.example.exception.customer.CustomerInUseException;
import org.example.exception.customer.UnkownCustomerException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Customer;

import java.util.Collection;

public interface CustomerService {

    Collection<Customer> getAllCustomers();
    void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException;
    void deleteCustomer(String email) throws UnkownCustomerException, CustomerInUseException;
    void updateCustomer(String email, Customer customer) throws UnkownCustomerException, UnknownStoreException, UnknownAddressException;
}
