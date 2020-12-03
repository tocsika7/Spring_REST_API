package org.example.dao.city;

import org.example.exception.city.CityInUseException;
import org.example.exception.city.InvalidCityException;
import org.example.exception.city.UnknownCityException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.City;

import java.util.Collection;

public interface CityDao {

    Collection<City> readAll();
    void createCity(City city) throws UnknownCountryException, InvalidCityException;
    void deleteCity(City city) throws UnknownCityException, CityInUseException;
    void updateCity(String cityName, City newCity) throws UnknownCityException, UnknownCountryException, InvalidCityException;
}
