package org.example.dao;

import org.example.exception.InvalidCountryException;
import org.example.model.Country;

import java.util.Collection;

public interface CountryDao {

    Collection<Country> readAll();
    void createCountry(Country country) throws InvalidCountryException;
}
