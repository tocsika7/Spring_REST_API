package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.CustomerEntity;
import org.example.exception.UnkownCustomerException;
import org.example.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRepository customerRepository;

    @Override
    public Collection<Customer> readAll() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(),false)
                .map(entity -> new Customer(
                        entity.getStore().getStoreId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getAddress().getId(),
                        entity.getActive()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public Customer readOne(int customerId){

            Optional<CustomerEntity> customerEntity = customerRepository.findById(customerId);
            if(customerEntity.isPresent()) {
                return customerEntity.map(entity -> new Customer(
                        entity.getStore().getStoreId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getAddress().getId(),
                        entity.getActive()
                )).get();
            }
            return null;
    }
}
