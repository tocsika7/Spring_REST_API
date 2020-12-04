package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.customer.CustomerDao;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.customer.CustomerInUseException;
import org.example.exception.customer.UnkownCustomerException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Override
    public Collection<Customer> getAllCustomers() {
        return customerDao.readAll();
    }

    @Override
    public void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException {
        customerDao.createCustomer(customer);
    }

    @Override
    public void deleteCustomer(String email) throws UnkownCustomerException, CustomerInUseException {
        customerDao.deleteCustomer(email);
    }

    @Override
    public void updateCustomer(String email, Customer customer) throws UnkownCustomerException, UnknownStoreException, UnknownAddressException {
        customerDao.updateCustomer(email,customer);
    }


}
