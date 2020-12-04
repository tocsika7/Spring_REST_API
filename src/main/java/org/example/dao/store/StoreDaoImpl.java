package org.example.dao.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Store;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreDaoImpl implements StoreDao {

    private final StoreRepository storeRepository;

    @Override
    public Collection<Store> readAll() {
        return StreamSupport.stream(storeRepository.findAll().spliterator(), false)
                .map(storeEntity -> new Store(
                        storeEntity.getStaff().getEmail(),
                        storeEntity.getAddress().getAddress()
                )).collect(Collectors.toList());
    }
}
