package org.example.dao.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Address;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Service
@RequiredArgsConstructor
public class AddressDaoImpl implements AddressDao {

    private final AddressRepository addressRepository;

    @Override
    public Collection<Address> readAll() {
        return StreamSupport.stream(addressRepository.findAll().spliterator(),false)
                .map(addressEntity -> new Address(
                        addressEntity.getAddress(),
                        addressEntity.getAddress2(),
                        addressEntity.getDistrict(),
                        addressEntity.getCity().getCity(),
                        addressEntity.getPostalCode(),
                        addressEntity.getPhone()
                )).collect(Collectors.toList());
    }
}
