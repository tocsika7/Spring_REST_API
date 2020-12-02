package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.country.CountryDao;
import org.example.exception.country.CountryInUseException;
import org.example.exception.country.InvalidCountryException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.Country;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryDao countryDao;

    @Override
    public Collection<Country> getAllCountries() {
        return countryDao.readAll();
    }

    @Override
    public void recordCounty(Country country) throws InvalidCountryException {
        countryDao.createCountry(country);
    }

    @Override
    public void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException {
        countryDao.deleteCountry(country);
    }

    @Override
    public void updateCountry(Country country, Country newCountry) throws UnknownCountryException, InvalidCountryException {
        countryDao.updateCountry(country,newCountry);
    }
}
