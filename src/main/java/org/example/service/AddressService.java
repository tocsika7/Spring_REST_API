package org.example.service;

import org.example.exception.city.UnknownCityException;
import org.example.model.Address;

import java.util.Collection;

public interface AddressService {

    Collection<Address> getAll();
    void createAddress(Address address) throws UnknownCityException;
}
