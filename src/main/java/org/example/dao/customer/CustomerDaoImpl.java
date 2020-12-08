package org.example.dao.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.address.AddressRepository;
import org.example.dao.entity.AddressEntity;
import org.example.dao.entity.CustomerEntity;
import org.example.dao.entity.StoreEntity;
import org.example.dao.store.StoreRepository;
import org.example.exception.customer.CustomerInUseException;
import org.example.exception.customer.InvalidCustomerException;
import org.example.exception.customer.UnkownCustomerException;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Customer;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final StoreRepository storeRepository;

    public AddressEntity queryAddress(String addressName) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = Optional.ofNullable(
                addressRepository.findFirstByAddress(addressName));
        if(addressEntity.isEmpty()){
            log.error(String.format("UnknownAddressException: Address: %s not found", addressName));
            throw new UnknownAddressException(String.format
                    ("Address not found: %s", addressName));
        }
        return addressEntity.get();
    }

    public StoreEntity queryStore(String storeAddress) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = Optional.ofNullable(
                storeRepository.findFirstByAddress_Address(storeAddress));
        if(storeEntity.isEmpty()){
            log.error(String.format("UnknownStoreException: Store at address: %s not found", storeAddress));
            throw new UnknownStoreException(String.format
                    ("Store at address: %s not found.", storeAddress));
        }
        return storeEntity.get();
    }

    @Override
    public Collection<Customer> readAll() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(),false)
                .map(entity -> new Customer(
                        entity.getStore().getAddress().getAddress(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getAddress().getAddress(),
                        entity.getActive()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException, InvalidCustomerException {
        CustomerEntity customerEntity;
        customerEntity = CustomerEntity.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .active(customer.getActive())
                .address(queryAddress(customer.getAddress()))
                .store(queryStore(customer.getStoreAddress()))
                .createDate(new Timestamp((new Date()).getTime()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("New Customer added: {} {}", customer.getFirstName(),
                customer.getLastName());
        try {
            customerRepository.save(customerEntity);
        } catch (Exception e){
            log.error("Error while saving new Customer: " + e.getMessage());
            throw new InvalidCustomerException("Cant create Customer with these parameters.");
        }
    }

    @Override
    public void deleteCustomer(String email) throws UnkownCustomerException, CustomerInUseException {
        Optional<CustomerEntity> customerEntity = Optional.ofNullable
                (customerRepository.findFirstByEmail(email));
        if(customerEntity.isEmpty()){
            log.error(String.format("UnknownCustomerException: Customer with email: %s not found",email));
            throw new UnkownCustomerException(String.format
                    ("Customer with email: %s not found", email ));
        }
        try {
            customerRepository.delete(customerEntity.get());
            log.info(String.format("Customer with email: %s deleted.",email));
        } catch (Exception e){
            log.error("Error while deleting Customer: " + e.getMessage());
            throw new CustomerInUseException(String.format("Customer: %s is used by other tables.", email));
        }
    }

    @Override
    public void updateCustomer(String email, Customer customer) throws UnkownCustomerException, UnknownAddressException, UnknownStoreException, InvalidCustomerException {
        Optional<CustomerEntity> customerEntity = Optional.ofNullable
                (customerRepository.findFirstByEmail(email));
        if(customerEntity.isEmpty()){
            log.error(String.format("UnknownCustomerException: Customer with email: %s not found",email));
            throw new UnkownCustomerException(String.format
                    ("Customer with email: %s not found", email ));
        }
        CustomerEntity newCustomerEntity;
        newCustomerEntity = CustomerEntity.builder()
                .customerId(customerEntity.get().getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .active(customer.getActive())
                .address(queryAddress(customer.getAddress()))
                .store(queryStore(customer.getStoreAddress()))
                .createDate(customerEntity.get().getCreateDate())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("Customer Updated: {} {}", customer.getFirstName(),
                customer.getLastName());
        try {
            customerRepository.save(newCustomerEntity);
        } catch (Exception e){
            log.error("Error while saving new Customer: " + e.getMessage());
            throw new InvalidCustomerException("Cant create Customer with these parameters.");
        }
    }

}
