package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.CountryEntity;
import org.example.exception.CountryInUseException;
import org.example.exception.InvalidCountryException;
import org.example.exception.UnknownCountryException;
import org.example.model.Country;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryDaoImpl implements CountryDao {

    private final CountryRepository countryRepository;

    @Override
    public Collection<Country> readAll() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(),false)
                .map(entity -> new Country(
                        entity.getCountry()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void createCountry(Country country) throws InvalidCountryException {
        CountryEntity countryEntity;

        countryEntity = CountryEntity.builder()
                .country(country.getCountry())
                .lastUpdate(new Timestamp(new Date().getTime()))
                .build();
        log.info("CountryEntity Created: {}",countryEntity);
        try{
            countryRepository.save(countryEntity);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new InvalidCountryException("Invalid country name");
        }
    }

    // TODO Fix SQLIntegrityConstraintViolationException
    @Override
    public void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException {
        Optional<CountryEntity> countryEntity = StreamSupport.stream(countryRepository.findAll().spliterator(),false)
                .filter(entity -> country.getCountry().equals(entity.getCountry())).findAny();
        if(countryEntity.isEmpty()){
            throw new UnknownCountryException(String.format("Country not found: %s", country));
        }
        try {
            countryRepository.delete(countryEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CountryInUseException("Country is used in an other table");
        }
    }
}
