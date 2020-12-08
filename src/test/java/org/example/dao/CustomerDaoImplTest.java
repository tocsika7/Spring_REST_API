package org.example.dao;

import org.example.dao.address.AddressRepository;
import org.example.dao.customer.CustomerDaoImpl;
import org.example.dao.customer.CustomerRepository;
import org.example.dao.entity.AddressEntity;
import org.example.dao.entity.CustomerEntity;
import org.example.dao.entity.StoreEntity;
import org.example.dao.store.StoreRepository;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.customer.CustomerInUseException;
import org.example.exception.customer.InvalidCustomerException;
import org.example.exception.customer.UnkownCustomerException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerDaoImplTest {

    @Spy
    @InjectMocks
    private CustomerDaoImpl customerDao;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreRepository storeRepository;

    @Test
    public void createCustomerSuccessful() throws UnknownStoreException, UnknownAddressException, InvalidCustomerException {
        doReturn(StoreEntity.builder().address(AddressEntity.builder().address("47 MySakila Drive").build()).build())
                .when(customerDao).queryStore(any());
        doReturn(AddressEntity.builder().address("1121 Loja Avenue").build())
                .when(customerDao).queryAddress(any());
        customerDao.createCustomer(getCustomer());
        verify(customerRepository,times(1)).save(any());
    }

    @Test
    public void createCustomerUnknownStore() throws UnknownAddressException {
        doReturn(AddressEntity.builder().address("1121 Loja Avenue").build())
                .when(customerDao).queryAddress(any());
        assertThrows(UnknownStoreException.class, ()-> {
            customerDao.createCustomer(getCustomerUnknownStore());
        });
    }

    @Test
    public void createCustomerUnknownAddress() {
        assertThrows(UnknownAddressException.class, ()-> {
            customerDao.createCustomer(getCustomerUnknownAddress());
        });
    }

    @Test
    public void deleteCustomerSuccessful() throws CustomerInUseException, UnkownCustomerException {
        doReturn(CustomerEntity.builder().email("john@gmail.com").build())
                .when(customerRepository).findFirstByEmail(anyString());
        customerDao.deleteCustomer("john@gmail.com");
        verify(customerRepository,times(1)).delete(any());
    }

    @Test
    public void deleteCustomerUnknownCustomer(){
        assertThrows(UnkownCustomerException.class, ()->{
           customerDao.deleteCustomer("unknown@customer.com");
        });
    }

    @Test
    public void updateCustomerSuccessful() throws UnkownCustomerException, UnknownStoreException, UnknownAddressException, InvalidCustomerException {
        doReturn(CustomerEntity.builder().email("john@gmail.com").build())
                .when(customerRepository).findFirstByEmail(anyString());
        doReturn(StoreEntity.builder().address(AddressEntity.builder().address("47 MySakila Drive").build()).build())
                .when(customerDao).queryStore(any());
        doReturn(AddressEntity.builder().address("1121 Loja Avenue").build())
                .when(customerDao).queryAddress(any());
        customerDao.updateCustomer("john@gmail.com", getCustomer());
        verify(customerRepository,times(1)).save(any());
    }

    @Test
    public void updateCustomerUnknownCustomer(){
        assertThrows(UnkownCustomerException.class, ()->{
            customerDao.updateCustomer("unknown@customer.com", getCustomer());
        });
    }

    @Test
    public void updateCustomerUnknownAddress(){
        doReturn(CustomerEntity.builder().email("john@gmail.com").build())
                .when(customerRepository).findFirstByEmail(anyString());
        assertThrows(UnknownAddressException.class, ()->{
            customerDao.updateCustomer("john@gmail.com", getCustomerUnknownAddress());
        });
    }

    @Test
    public void updateCustomerUnknownStore() throws UnknownAddressException {
        doReturn(CustomerEntity.builder().email("john@gmail.com").build())
                .when(customerRepository).findFirstByEmail(anyString());
        doReturn(AddressEntity.builder().address("1121 Loja Avenue").build())
                .when(customerDao).queryAddress(any());
        assertThrows(UnknownStoreException.class, ()->{
           customerDao.updateCustomer("john@gmail.com", getCustomerUnknownStore());
        });
    }

    public Customer getCustomer(){
        return new Customer(
                "47 MySakila Drive",
                "John",
                "Doe",
                "john@gmail.com",
                "1121 Loja Avenue",
                1
        );
    }

    public Customer getCustomerUnknownStore(){
        return new Customer(
                "",
                "John",
                "Doe",
                "john@gmail.com",
                "1121 Loja Avenue",
                1
        );
    }

    public Customer getCustomerUnknownAddress(){
        return new Customer(
                "47 MySakila Drive",
                "John",
                "Doe",
                "john@gmail.com",
                "",
                1
        );
    }




}






