package org.example.dao.city;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.City;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityDaoImpl implements CityDao {

    private final CityRepository cityRepository;

    @Override
    public Collection<City> readAll() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(),false)
                .map(cityEntity -> new City(
                        cityEntity.getCity(),
                        cityEntity.getCountry().getCountry()
                )).collect(Collectors.toList());
    }
}
