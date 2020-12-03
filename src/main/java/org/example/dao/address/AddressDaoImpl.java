package org.example.dao.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.city.CityRepository;
import org.example.dao.entity.AddressEntity;
import org.example.dao.entity.CityEntity;
import org.example.exception.address.AddressInUseException;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.city.UnknownCityException;
import org.example.model.Address;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Service
@RequiredArgsConstructor
public class AddressDaoImpl implements AddressDao {

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;

    protected CityEntity queryCity(String cityName) throws UnknownCityException {
        Optional<CityEntity> cityEntity = Optional.ofNullable(cityRepository.findCityEntityByCity(cityName));
        if(cityEntity.isEmpty()){
            throw new UnknownCityException("City not found: " + cityName);
        }
        return cityEntity.get();
    }

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

    @Override
    public void createAddress(Address address) throws UnknownCityException {
        AddressEntity addressEntity;
        GeometryFactory geometryFactory = new GeometryFactory();
        addressEntity = AddressEntity.builder()
                .address(address.getAddress())
                .address2(address.getAddress2())
                .district(address.getDistrict())
                .city(queryCity(address.getCityName()))
                .phone(address.getPhone())
                .location(geometryFactory.createPoint(new Coordinate()))
                .postalCode(address.getPostalCode())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("Address entity created: " + addressEntity.toString());
        try {
            addressRepository.save(addressEntity);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public void deleteAddress(Address address) throws UnknownAddressException, AddressInUseException {
        Optional<AddressEntity> addressEntity = Optional.
                ofNullable(addressRepository.
                findAddressEntityByAddress(address.getAddress()));
        if(addressEntity.isEmpty()){
            throw new UnknownAddressException("Address not found");
        }
        try {
            addressRepository.delete(addressEntity.get());
        } catch (Exception e){
            log.error("Error while deleting Address: " + e.getMessage());
            throw new AddressInUseException("Address used by an other table" + address.getAddress());
        }
    }
}
