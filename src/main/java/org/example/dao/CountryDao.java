package org.example.dao;

import org.example.exception.CountryInUseException;
import org.example.exception.InvalidCountryException;
import org.example.exception.UnknownCountryException;
import org.example.model.Country;

import java.util.Collection;

public interface CountryDao {

    Collection<Country> readAll();
    void createCountry(Country country) throws InvalidCountryException;
    void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException;
}
