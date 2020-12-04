package org.example.service.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.address.AddressDao;
import org.example.exception.address.AddressInUseException;
import org.example.exception.address.InvalidAddressException;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.city.UnknownCityException;
import org.example.model.Address;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    @Override
    public Collection<Address> getAll() {
        return addressDao.readAll();
    }

    @Override
    public void createAddress(Address address) throws UnknownCityException {
        addressDao.createAddress(address);
    }

    @Override
    public void deleteAddress(Address address) throws UnknownAddressException, AddressInUseException {
        addressDao.deleteAddress(address);
    }

    @Override
    public void updateAddress(String addressName, Address newAddress) throws UnknownAddressException, UnknownCityException, InvalidAddressException {
        addressDao.updateAddress(addressName, newAddress);
    }
}
