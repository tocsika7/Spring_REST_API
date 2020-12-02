package org.example.service;

import org.example.exception.country.CountryInUseException;
import org.example.exception.country.InvalidCountryException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.Country;

import java.util.Collection;

public interface CountryService {

    Collection<Country> getAllCountries();
    void recordCounty(Country country) throws InvalidCountryException;
    void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException;
    void updateCountry(Country country, Country newCountry) throws  UnknownCountryException, InvalidCountryException;
}
