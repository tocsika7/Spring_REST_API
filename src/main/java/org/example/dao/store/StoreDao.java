package org.example.dao.store;

import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.InvalidStoreException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Store;

import java.util.Collection;

public interface StoreDao {

    Collection<Store> readAll();
    Store readById(int storeId) throws UnknownStoreException;
    void updateStoreAddress(String currentAddress, String newAddress) throws UnknownStoreException, UnknownAddressException, InvalidStoreException;
}
