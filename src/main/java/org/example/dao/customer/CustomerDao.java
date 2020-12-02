package org.example.dao.customer;

import org.example.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    Collection<Customer> readAll();
    Customer readOne(int customerId);
}
