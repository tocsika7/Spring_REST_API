package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.CustomerDao;
import org.example.exception.UnkownCustomerException;
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
    public Customer getCustomer(int customerId) {
        return customerDao.readOne(customerId);
    }

}
