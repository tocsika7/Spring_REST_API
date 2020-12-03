package org.example.dao.store;

import org.example.dao.entity.StoreEntity;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<StoreEntity, Integer> {
    StoreEntity findFirstByAddress_Address(String storeAddress);
}
