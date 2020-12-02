package org.example.dao.city;

import org.example.model.City;

import java.util.Collection;

public interface CityDao {

    Collection<City> readAll();
}
