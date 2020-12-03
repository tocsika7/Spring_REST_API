package org.example.dao.address;

import org.example.dao.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;


public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
    AddressEntity findAddressEntityByAddress(String address);
}
