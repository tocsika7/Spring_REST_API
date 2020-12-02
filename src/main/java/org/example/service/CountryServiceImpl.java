package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.CountryDao;
import org.example.exception.InvalidCountryException;
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
}
