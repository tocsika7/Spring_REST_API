package org.example.service.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.store.StoreDao;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Store;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao;

    @Override
    public Collection<Store> readAll() {
        return storeDao.readAll();
    }

    @Override
    public Store readById(int storeId) throws UnknownStoreException {
        return storeDao.readById(storeId);
    }

    @Override
    public void updateStoreAddress(String currentAddress, String newAddress) throws UnknownAddressException, UnknownStoreException {
        storeDao.updateStoreAddress(currentAddress,newAddress);
    }
}
