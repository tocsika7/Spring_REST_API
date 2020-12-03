package org.example.dao.address;

import org.example.model.Address;

import java.util.Collection;

public interface AddressDao {

    Collection<Address> readAll();
}
