package org.example.dao;

import org.example.model.Country;

import java.util.Collection;

public interface CountryDao {

    Collection<Country> readAll();
}
