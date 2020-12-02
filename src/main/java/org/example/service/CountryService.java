package org.example.service;

import org.example.exception.CountryInUseException;
import org.example.exception.InvalidCountryException;
import org.example.exception.UnknownCountryException;
import org.example.model.Country;

import java.util.Collection;

public interface CountryService {

    Collection<Country> getAllCountries();
    void recordCounty(Country country) throws InvalidCountryException;
    void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException;
}
