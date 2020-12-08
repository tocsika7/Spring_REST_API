package org.example.dao.country;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.CountryEntity;
import org.example.exception.country.CountryInUseException;
import org.example.exception.country.InvalidCountryException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.Country;
import org.springframework.stereotype.Service;

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
            log.error("Error while saving new Country: " + e.getMessage());
            throw new InvalidCountryException("Cant create Country with these parameters.");
        }
    }

    @Override
    public void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException {
        Optional<CountryEntity> countryEntity = Optional.
                ofNullable(countryRepository.findFirstByCountry(country.getCountry()));
        if(countryEntity.isEmpty()){
            log.error(String.format("UnknownCountryException: Country not found: %s", country));
            throw new UnknownCountryException(String.format("Country not found: %s", country));
        }
        try {
            log.info(String.format("Country %s deleted.", country.getCountry()));
            countryRepository.delete(countryEntity.get());
        } catch (Exception e) {
            log.error(String.format("Error: Country %s is used by another table.", country.getCountry()));
            throw new CountryInUseException(String.format("Country %s is used by another table.", country.getCountry()));
        }
    }

    @Override
    public void updateCountry(Country country, Country newCountry) throws UnknownCountryException, InvalidCountryException {
        Optional<CountryEntity> countryEntity = Optional.ofNullable(countryRepository.findFirstByCountry(country.getCountry()));
        if(countryEntity.isEmpty()){
            log.error(String.format("UnknownCountryException: Country not found: %s", country.getCountry()));
            throw new UnknownCountryException(String.format("Country not found: %s", country.getCountry()));
        }
        else {
            CountryEntity newCountryEntity = CountryEntity.builder()
                    .countryId(countryEntity.get().getCountryId())
                    .country(newCountry.getCountry())
                    .lastUpdate(new Timestamp(new Date().getTime()))
                    .build();
            log.info("CountryEntity Created: {}", countryEntity.get());
            try {
                countryRepository.save(newCountryEntity);
            } catch (Exception e) {
                log.error("Error while saving new Country: " + e.getMessage());
                throw new InvalidCountryException("Cant create Country with these parameters.");
            }
        }
    }
}
