package org.example.dao;

import org.example.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    Collection<Customer> readAll();
    Customer readOne(int customerId);
}
