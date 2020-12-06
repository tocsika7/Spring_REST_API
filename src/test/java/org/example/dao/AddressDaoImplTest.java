package org.example.dao;


import org.example.dao.address.AddressDaoImpl;
import org.example.dao.address.AddressRepository;
import org.example.dao.city.CityRepository;
import org.example.dao.entity.AddressEntity;
import org.example.dao.entity.CityEntity;
import org.example.exception.address.AddressInUseException;
import org.example.exception.address.InvalidAddressException;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.city.UnknownCityException;
import org.example.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressDaoImplTest {

    @Spy
    @InjectMocks
    private AddressDaoImpl addressDao;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CityRepository cityRepository;

    @Test
    public void createAddressSuccessful() throws UnknownCityException {

        doReturn(CityEntity.builder().city("Batna").build())
                .when(addressDao).queryCity(anyString());
        addressDao.createAddress(getAddressValid());
        verify(addressRepository,times(1)).save(any());
    }

    @Test
    public void createAddressWithInvalidCity() {
        assertThrows(UnknownCityException.class, ()-> {
            addressDao.createAddress(getInvalidAddress());
        });
    }

    @Test
    public void deleteAddressSuccessful() throws UnknownAddressException, AddressInUseException {
        doReturn(AddressEntity.builder().address("New Addr").build())
                .when(addressRepository).findFirstByAddress(any());
        addressDao.deleteAddress(getValidAddressToDelete());
        verify(addressRepository,times(1)).delete(any());
    }

    @Test
    public void deleteAddressInvalidAddress() {
       assertThrows(UnknownAddressException.class, () -> {
           addressDao.deleteAddress(getInvalidAddressByAddressToDelete());
       });
    }

    @Test
    public void updateAddressSuccessful() throws UnknownCityException, InvalidAddressException, UnknownAddressException {
        doReturn(AddressEntity.builder().address("New Addr").build())
                .when(addressRepository).findFirstByAddress(any());
        doReturn(CityEntity.builder().city("Batna").build())
                .when(addressDao).queryCity(anyString());
        addressDao.updateAddress("New Addr", getAddressValid());
        verify(addressRepository,times(1)).save(any());
    }

    @Test
    public void updateAddressUnknownAddress() {
        assertThrows(UnknownAddressException.class, () -> {
            addressDao.updateAddress("Unknown Address", getValidAddressToDelete());
        });
    }

    @Test
    public void updateAddressUnknownCity() {
        doReturn(AddressEntity.builder().address("New Addr").build())
                .when(addressRepository).findFirstByAddress(any());
        assertThrows(UnknownCityException.class, ()-> {
            addressDao.updateAddress("New Addr", getInvalidAddressByCity());
        });
    }



    private Address getAddressValid() {
        return new Address(
                "New Address",
                "no",
                "Test district",
                "Batna",
                "0",
                "12345");
    }

    private Address getInvalidAddress(){
        return new Address(
                "New Address",
                "no",
                "Test district",
                "Unknown City",
                "0",
                "12345");
    }

    private Address getValidAddressToDelete(){
        return new Address(
                "New Addr",
                "",
                "123",
                "Batna",
                "123",
                "123"
        );
    }

    private Address getInvalidAddressByAddressToDelete(){
        return new Address(
                "Invalid",
                "",
                "123",
                "Batna",
                "123",
                "123"
        );
    }

    private Address getInvalidAddressByCity(){
        return new Address(
                "New Addr",
                "",
                "123",
                "Unknown City",
                "123",
                "123"
        );
    }




}
