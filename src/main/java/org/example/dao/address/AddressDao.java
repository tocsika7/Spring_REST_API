package org.example.dao.address;

import org.example.exception.address.AddressInUseException;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.city.UnknownCityException;
import org.example.model.Address;

import java.util.Collection;

public interface AddressDao {

    Collection<Address> readAll();
    void createAddress(Address address) throws UnknownCityException;
    void deleteAddress(Address address) throws UnknownAddressException, AddressInUseException;
}
