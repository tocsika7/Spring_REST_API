package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.store.StoreDao;
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
}
