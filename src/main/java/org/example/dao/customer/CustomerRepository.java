package org.example.dao.customer;

import org.example.dao.entity.CustomerEntity;
import org.example.dao.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {
    CustomerEntity findFirstByEmail(String email);
}
